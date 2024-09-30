package com.tallerwebi.dominio.model;


import javax.persistence.*;

import java.util.List;


@Entity
public class Review extends Publicacion {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false, name = "rating")
    private Rating rating;

    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;

    public Review() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }


}
