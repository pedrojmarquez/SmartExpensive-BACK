package com.smartexpensive.backend.domain.models.dao;

import com.smartexpensive.backend.domain.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioDao  extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
