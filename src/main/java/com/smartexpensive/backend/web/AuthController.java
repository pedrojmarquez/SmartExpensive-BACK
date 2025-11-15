package com.smartexpensive.backend.web;

import com.smartexpensive.backend.domain.models.entity.Usuario;
import com.smartexpensive.backend.domain.services.Usuario.UsuarioServiceImpl;
import com.smartexpensive.backend.security.dto.LoginDTO;
import com.smartexpensive.backend.security.dto.RegistroDTO;
import com.smartexpensive.backend.security.services.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.Map;

@Controller
@CrossOrigin(origins = "http://localhost:8100")
public class AuthController {

    private final UsuarioServiceImpl usuarioService;
    private final JwtService jwtService;

    public AuthController(UsuarioServiceImpl usuarioService, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }




    @GetMapping("/login/success")
    public void loginSuccess(@AuthenticationPrincipal OAuth2User oAuth2User,
                             HttpServletResponse response) throws IOException {

        // Crear o recuperar usuario
        Usuario usuario = usuarioService.procesarOAuthUsuario(oAuth2User);

        // Generar JWT
        String token = jwtService.generateToken(usuario.getEmail(), usuario.getNombre());

        // Redirigir al frontend con el token
        String frontendUrl = "http://localhost:8100/home?token=" + token;
        response.sendRedirect(frontendUrl);
    }

    @PostMapping("auth/register")
    public ResponseEntity<?> register(@RequestBody RegistroDTO request) {
        try {
            Usuario usuario = this.usuarioService.registrarUsuario(request.getNombre(), request.getEmail(), request.getPassword());
            // Generamos el JWT
            String token = jwtService.generateToken(usuario.getEmail(), usuario.getNombre());

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "usuario", usuario
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO request) {
        try {
            Usuario usuario = this.usuarioService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(jwtService.generateToken(usuario.getEmail(), usuario.getNombre()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
