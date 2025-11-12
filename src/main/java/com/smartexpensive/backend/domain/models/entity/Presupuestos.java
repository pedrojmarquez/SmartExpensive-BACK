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
@Table(name = "PRESUPUESTOS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_usuario", "mes", "anio"})
})
public class Presupuestos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_presupuesto")
    private Long idPresupuesto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;

    private int mes;

    private int anio;

    @Column(name = "monto_total")
    private Double montoTotal;

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
