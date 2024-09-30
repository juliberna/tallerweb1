package com.tallerwebi.dominio.model;

import javax.persistence.Column;

import java.time.LocalDateTime;



public abstract class Publicacion {


    @Column(name = "user_commentary", nullable = false)
    protected String textoComentario;
    @Column(name = "user_name", nullable = false)
    protected Usuario Usuario;
    @Column(name = "like", nullable = true)
    protected Integer likes = 0;
    @Column(name = "dislike", nullable = true)
    protected Integer dislikes = 0;
    @Column(name = "publication_date", nullable = false)
    protected LocalDateTime fechaPublicacion;

    public Publicacion() {
        fechaPublicacion = LocalDateTime.now();
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


