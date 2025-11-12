package com.smartexpensive.backend.domain.models.dao;

import com.smartexpensive.backend.domain.models.entity.Presupuestos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPresupuestosDao extends JpaRepository<Presupuestos, Long> {
}
