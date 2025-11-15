package com.smartexpensive.backend.domain.services.Usuario;

import com.smartexpensive.backend.domain.models.dao.IUsuarioDao;
import com.smartexpensive.backend.domain.models.entity.Usuario;
import com.smartexpensive.backend.security.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

    private final IUsuarioDao usuarioDao;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;


    public UsuarioServiceImpl(IUsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
        this.passwordEncoder = new BCryptPasswordEncoder();

    }

    public Usuario procesarOAuthUsuario(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");

        return usuarioDao.findByEmail(email).orElseGet(() -> {
            Usuario u = new Usuario();
            u.setEmail(email);
            u.setNombre(oAuth2User.getAttribute("name"));
            u.setImagen(oAuth2User.getAttribute("picture"));
            u.setPassword(null);
            return usuarioDao.save(u);
        });
    }

    // Login de usuario
    public Usuario login(String email, String password) throws Exception {
        Usuario usuario = usuarioDao.findByEmail(email)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new Exception("Contraseña incorrecta");
        }

        return usuario;
    }

    // Registro de usuario
    public Usuario registrarUsuario(String nombre, String email, String password) throws Exception {
        Optional<Usuario> usuarioExistente = usuarioDao.findByEmail(email);
        if (usuarioExistente.isPresent()) {
            throw new Exception("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(password)); // Hash de la contraseña
        return usuarioDao.save(usuario);
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
