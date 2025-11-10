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

        // Spring necesita un UserDetails, aunque no uses password (OAuth2)
        return User.builder()
                .username(usuario.getEmail())
                .password("") // vacío porque Google maneja la autenticación
                .roles("USER")
                .build();
    }
}
