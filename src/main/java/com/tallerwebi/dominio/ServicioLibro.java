package com.tallerwebi.dominio;

public interface ServicioLibro {
    Libro obtenerIdLibro(Long id);
    void actualizarLibro(Libro libro);
}
