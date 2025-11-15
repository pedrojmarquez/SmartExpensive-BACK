package com.smartexpensive.backend.security.services;

import com.smartexpensive.backend.domain.models.dao.IUsuarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUsuarioDao usuarioDao;

    @Autowired
    public CustomUserDetailsService(IUsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var usuario = usuarioDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        // Si el usuario tiene password (registro normal) se lo ponemos, si no, dejamos vacío (Google OAuth)
        String password = usuario.getPassword() != null ? usuario.getPassword() : "";

        return User.builder()
                .username(usuario.getEmail())
                .password(password)
                .roles("USER")
                .build();
    }

}
