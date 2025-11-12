package com.smartexpensive.backend.domain.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetallePresupuestoDTO {
    private Long idDetalle;
    private Long idPresupuesto;
    private Long idCategoria;
    private Double montoAsignado;
    private Double montoGastado;
    private LocalDate creadoEn;
    private LocalDate actualizadoEn;
}
