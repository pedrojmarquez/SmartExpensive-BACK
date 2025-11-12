package com.smartexpensive.backend.domain.dto;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriasDTO {
    private Long idCategoria;
    private String nombre;
    private String grupo;
    private String icono;
    private String colorHex;
    private boolean esPersonal;
    private Long idUsuario;
}
