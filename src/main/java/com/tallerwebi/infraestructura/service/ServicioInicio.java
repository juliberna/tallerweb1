package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.model.Usuario;

public interface ServicioInicio {

    public void buscar(String nombre);

    public void cargarReviews();

    public void recomendarLibro(Usuario usuario);




}
