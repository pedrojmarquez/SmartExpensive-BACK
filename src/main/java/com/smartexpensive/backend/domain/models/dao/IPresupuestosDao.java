package com.smartexpensive.backend.domain.models.dao;

import com.smartexpensive.backend.domain.models.entity.Presupuestos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IPresupuestosDao extends JpaRepository<Presupuestos, Long> {


    @Query("SELECT p FROM Presupuestos p WHERE p.mes = :mes AND p.anio = :anio AND p.usuario.id = :usuario")
    Presupuestos findPresupuesto(
            @Param("usuario") Long usuario,
            @Param("mes") int mes,
            @Param("anio") int anio
    );
}
