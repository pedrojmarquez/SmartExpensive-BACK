package com.smartexpensive.backend.security.web;

import com.smartexpensive.backend.domain.models.entity.Usuario;
import com.smartexpensive.backend.domain.services.Usuario.UsuarioServiceImpl;
import com.smartexpensive.backend.security.services.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
@CrossOrigin(origins = "http://localhost:8100")
public class AuthController {

    private final UsuarioServiceImpl usuarioService;
    private final JwtService jwtService;

    public AuthController(UsuarioServiceImpl usuarioService, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }

    // Este endpoint se ejecuta justo después del login con Google
    @GetMapping("/login/success")
    public void loginSuccess(@AuthenticationPrincipal OAuth2User principal, HttpServletResponse response) throws IOException {
        if (principal != null) {
            // Procesamos y guardamos al usuario si no existe
            Usuario usuario = usuarioService.procesarOAuthUsuario(principal);

            // Generamos el JWT
            String token = jwtService.generateToken(usuario.getEmail(), usuario.getNombre());

            // Redirigimos al frontend con el token en la URL
            String redirectUrl = "http://localhost:8100/home?token=" + token;
            response.sendRedirect(redirectUrl);
        } else {
            response.sendRedirect("http://localhost:8100/login?error=true");
        }
    }
}
