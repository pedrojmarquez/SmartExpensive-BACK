package com.smartexpensive.backend.domain.models.dao;

import com.smartexpensive.backend.domain.models.entity.DetallePresupuesto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDetallePresupuestoDao extends JpaRepository<DetallePresupuesto, Long> {
    List<DetallePresupuesto> findAllByIdPresupuesto(Long idPresupuesto);
}
