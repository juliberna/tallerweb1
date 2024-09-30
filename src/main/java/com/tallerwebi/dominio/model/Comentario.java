package com.tallerwebi.dominio.model;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Calendar;

public class Comentario extends Publicacion {

    @Id
    private long id;

    @ManyToOne
    private Review review;

    public Comentario( ) {
        super();
    }

}
