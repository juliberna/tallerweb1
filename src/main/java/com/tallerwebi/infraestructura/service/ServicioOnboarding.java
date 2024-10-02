package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.model.Autor;
import com.tallerwebi.dominio.model.Genero;
import com.tallerwebi.dominio.model.Libro;

import java.util.List;

public interface ServicioOnboarding {
    List<Genero> obtenerGeneros();

    void guardarGeneros(Long usuarioId, List<Long> generos);
}
