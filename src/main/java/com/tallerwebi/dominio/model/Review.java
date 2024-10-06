package com.tallerwebi.dominio.model;


import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
public class Review {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, name = "rating")
    private Rating rating;

    @Column(name = "user_commentary", nullable = false)
    private String textoComentario;


    @Column(name = "likes_count", nullable = true)
    private Integer likes = 0;

    @Column(name = "dislikes_count", nullable = true)
    private Integer dislikes = 0;

    @Column(name = "publication_date", nullable = false)
    private LocalDateTime fechaPublicacion;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario Usuario;

    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

    public Review() {
        super();
        fechaPublicacion = LocalDateTime.now();
    }

    public Long getId() {
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
    public com.tallerwebi.dominio.model.Usuario getUsuario() {
        return Usuario;
    }

    public String getTextoComentario() {
        return textoComentario;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public Integer getLikes() {
        return likes;
    }

    public Integer getDislikes() { return dislikes; }

    public void setTextoComentario(String textoComentario) {
        this.textoComentario = textoComentario;
    }

    public void setUsuario(com.tallerwebi.dominio.model.Usuario usuario) {
        Usuario = usuario;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

}
