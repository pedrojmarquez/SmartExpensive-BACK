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
@Table(name = "GASTOS")
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "nombre_comercio")
    private String nombreComercio;
    private Double total;


    @Column(name = "descripcion_gasto")
    private String descripcionGasto;

    @Column(name = "fecha_gasto")
    private LocalDate fechaGasto;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria" , referencedColumnName = "id_categoria")
    private Categorias categoria;

}
