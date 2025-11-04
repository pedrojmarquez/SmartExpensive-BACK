package com.smartexpensive.backend.web;


import com.smartexpensive.backend.domain.models.entity.Usuario;
import com.smartexpensive.backend.domain.services.Usuario.UsuarioServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;

    public UsuarioController(UsuarioServiceImpl usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/api/me")
    public Usuario getUsuario(@AuthenticationPrincipal OAuth2User principal) {
        return usuarioService.procesarOAuthUsuario(principal);
    }
}
