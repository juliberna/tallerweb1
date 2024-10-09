package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.excepcion.ListaDeReviewsVacias;
import com.tallerwebi.dominio.excepcion.ListaVacia;
import com.tallerwebi.dominio.model.Review;
import com.tallerwebi.dominio.model.Usuario;

import java.util.List;

public interface ServicioInicio {

    public void buscar(String nombre);

    public List<Review> cargarTodasLasReviews() throws ListaDeReviewsVacias;

    public void recomendarLibro(Usuario usuario);




}
