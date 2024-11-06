package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.dominio.model.Resenia;
import com.tallerwebi.dominio.model.Usuario;

import java.util.List;

public interface ServicioResenia {
    void guardarResenia(Usuario usuario, Libro libro,Integer puntuacion,String descripcion);
    List<Resenia> obtenerReseniasDeOtrosUsuarios(Long userId,Long idLibro);
    Double calcularPromedioPuntuacion(Long idLibro);

    Resenia obtenerReseniaDelUsuario(Long userId,Long idLibro);
}
