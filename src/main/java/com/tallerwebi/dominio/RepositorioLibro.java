package com.tallerwebi.dominio;

public interface RepositorioLibro {
    Libro buscarLibro(Long id);
    void actualizarLibro(Libro libro);
    String guardarLibro(Libro libro);
}
