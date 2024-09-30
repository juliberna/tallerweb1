package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.excepcion.ListaVacia;
import com.tallerwebi.dominio.excepcion.QueryVacia;
import com.tallerwebi.dominio.model.Libro;

import java.util.Set;

public interface ServicioLibro {
    Set<Libro> buscar(String query) throws QueryVacia, ListaVacia;
    Libro obtenerIdLibro(Long id);
    void actualizarLibro(Libro libro);

}
