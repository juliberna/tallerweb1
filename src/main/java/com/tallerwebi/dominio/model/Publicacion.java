package com.tallerwebi.dominio.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;


public abstract class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    protected String textoComentario;
    protected Usuario Usuario;
    protected Integer likes;
    protected Integer dislikes;
    protected Calendar fechaPublicacion;

    public Publicacion(com.tallerwebi.dominio.model.Usuario usuario, String textoComentario) {
    }

    public Integer getId() {
        return id;
    }

    public com.tallerwebi.dominio.model.Usuario getUsuario() {
        return Usuario;
    }

    public String getTextoComentario() {
        return textoComentario;
    }

    public Calendar getFechaPublicacion() {
        return fechaPublicacion;
    }

    public Integer getLikes() {
        return likes;
    }
}
