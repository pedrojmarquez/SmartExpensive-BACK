package com.smartexpensive.backend.domain.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GastosDTO {
    private Long id;
    private String nombreComercio;
    private Double total;
    private String categoriaGasto;
    private String descripcionGasto;
    private LocalDate fechaGasto;
    private Long usuarioId;
}
