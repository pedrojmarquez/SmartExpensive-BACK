package com.smartexpensive.backend.domain.models.dao;

import com.smartexpensive.backend.domain.models.entity.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriasDao extends JpaRepository<Categorias, Long> {
}
