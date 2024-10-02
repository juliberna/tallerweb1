package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.Autor;
import com.tallerwebi.dominio.model.Genero;
import com.tallerwebi.dominio.model.Libro;

import java.util.List;

public interface RepositorioOnboarding {
    List<Genero> obtenerGeneros();

    Genero obtenerGeneroPorId(Long idGenero);
}
