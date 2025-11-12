package com.smartexpensive.backend.domain.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "CATEGORIAS")
public class Categorias implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    private String nombre;

    private String grupo;

    private String icono;

    @Column(name = "color_hex")
    private String colorHex;

    @Column(name = "es_personal")
    private boolean esPersonal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;

}
