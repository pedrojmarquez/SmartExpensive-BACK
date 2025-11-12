package com.smartexpensive.backend.domain.models.dao;

import com.smartexpensive.backend.domain.models.entity.DetallePresupuesto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDetallePresupuestoDao extends JpaRepository<DetallePresupuesto, Long> {
}
