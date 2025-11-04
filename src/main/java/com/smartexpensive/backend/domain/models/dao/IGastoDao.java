package com.smartexpensive.backend.domain.models.dao;

import com.smartexpensive.backend.domain.models.entity.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGastoDao extends JpaRepository<Gasto, Long> {
}
