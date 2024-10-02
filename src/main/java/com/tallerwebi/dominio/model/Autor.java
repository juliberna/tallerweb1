package com.tallerwebi.dominio.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @ManyToMany
    @JoinTable(
            name = "autor_genero",
            joinColumns = @JoinColumn(name = "autor_id"),
            inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    private Set<Genero> generos = new HashSet<>();
}
