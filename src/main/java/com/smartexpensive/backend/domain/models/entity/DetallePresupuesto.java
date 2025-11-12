package com.smartexpensive.backend.domain.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "DETALLE_PRESUPUESTO", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_presupuesto", "id_categoria"})
})
public class DetallePresupuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;

    @Column(name = "id_presupuesto")
    private Long idPresupuesto;

    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(name = "monto_asignado")
    private Double montoAsignado;

    @Column(name = "monto_gastado")
    private Double montoGastado;

    @Column(name = "creado_en")
    private LocalDate creadoEn;

    @Column(name = "actualizado_en")
    private LocalDate actualizadoEn;


    @PreUpdate
    public void preUpdate() {
        this.actualizadoEn = LocalDate.now();
    }

    @PrePersist
    public void prePersist() {
        this.creadoEn = LocalDate.now();
        this.actualizadoEn = LocalDate.now();
    }
}
