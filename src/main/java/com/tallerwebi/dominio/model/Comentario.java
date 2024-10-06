package com.tallerwebi.dominio.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Calendar;

public class Comentario{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_commentary", nullable = false)
    private String textoComentario;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario Usuario;

    @Column(name = "like", nullable = true)
    private Integer likes = 0;

    @Column(name = "dislike", nullable = true)
    private Integer dislikes = 0;

    @Column(name = "publication_date", nullable = false)
    private LocalDateTime fechaPublicacion;


    @ManyToOne
    private Review review;

    public Comentario( ) {

        fechaPublicacion = LocalDateTime.now();
    }

    public long getId() {
        return id;
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
