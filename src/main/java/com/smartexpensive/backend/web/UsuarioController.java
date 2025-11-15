package com.smartexpensive.backend.web;


import com.smartexpensive.backend.domain.models.dao.IUsuarioDao;
import com.smartexpensive.backend.domain.models.entity.Usuario;
import com.smartexpensive.backend.domain.services.Usuario.UsuarioServiceImpl;
import com.smartexpensive.backend.security.dto.LoginDTO;
import com.smartexpensive.backend.security.dto.RegistroDTO;
import com.smartexpensive.backend.security.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8100")
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;

    @Autowired
    private IUsuarioDao usuarioDao;

    @Autowired
    private JwtService jwtService;

    public UsuarioController(UsuarioServiceImpl usuarioService) {
        this.usuarioService = usuarioService;
    }


    @GetMapping("/api/usuario")
    public Usuario getUsuario(HttpServletRequest request) {
        return usuarioService.obtenerUsuario(request);
    }



}
