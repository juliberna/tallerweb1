package com.tallerwebi.dominio.model;

import java.util.Calendar;

public class Comentario extends Publicacion {


    public Comentario( Usuario usuario, String textoComentario) {
        super(usuario,textoComentario);
        this.textoComentario = textoComentario;
        this.Usuario = usuario;
        this.fechaPublicacion = Calendar.getInstance();

    }

}
