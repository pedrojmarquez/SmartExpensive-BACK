package com.smartexpensive.backend.domain.dto;


import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PresupuestosDTO {
    private Long idPresupuesto;
    private Long idUsuario;
    private int mes;
    private int anio;
    private Double montoTotal;
    private LocalDate creadoEn;
    private LocalDate actualizadoEn;
    private List<DetallePresupuestoDTO> detallePresupuesto;
}
