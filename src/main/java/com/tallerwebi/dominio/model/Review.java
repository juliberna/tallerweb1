package com.tallerwebi.dominio.model;


import java.util.TreeSet;

public class Review extends Publicacion {

    private Rating rating;
    private Libro libro;
    TreeSet<Comentario> comentarios;

    public Review(Usuario usuario, String textoComentario, Rating rating) {
        super(usuario, textoComentario);
        this.rating = rating;
    }


    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }


}
