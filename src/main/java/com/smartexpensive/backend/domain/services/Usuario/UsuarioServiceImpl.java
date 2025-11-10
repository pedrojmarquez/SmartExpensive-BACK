package com.smartexpensive.backend.domain.services.Usuario;

import com.smartexpensive.backend.domain.models.dao.IUsuarioDao;
import com.smartexpensive.backend.domain.models.entity.Usuario;
import com.smartexpensive.backend.security.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.oauth2.core.user.OAuth2User;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

    private final IUsuarioDao usuarioDao;

    @Autowired
    private JwtService jwtService;

    public UsuarioServiceImpl(IUsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public Usuario procesarOAuthUsuario(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");

        return usuarioDao.findByEmail(email).orElseGet(() -> {
            Usuario u = new Usuario();
            u.setEmail(email);
            u.setNombre(oAuth2User.getAttribute("name"));
            u.setImagen(oAuth2User.getAttribute("picture"));
            return usuarioDao.save(u);
        });
    }

    public Usuario procesarUsuarioPorEmail(String email) {
        return usuarioDao.findByEmail(email).orElseGet(() -> {
            Usuario u = new Usuario();
            u.setEmail(email);
            u.setNombre("Invitado"); // o algún valor por defecto
            u.setImagen(null);
            return usuarioDao.save(u);
        });
    }

    @Override
    public Usuario obtenerUsuario(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        // Quitar el prefijo "Bearer " si está presente
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String email = jwtService.extractUsername(token);
        System.out.println(email);
        return usuarioDao.findByEmail(email).orElse(null);
    }

}
