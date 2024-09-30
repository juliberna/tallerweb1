package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.Libro;

import java.util.List;

public interface RepositorioLibro {
    List<Libro> buscar(String query);
    Libro buscarLibro(Long id);
    void actualizarLibro(Libro libro);
    List<Libro> buscarPorEstadoDeLectura(String estadoDeLectura);
}
