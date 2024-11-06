package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.excepcion.ReseniaInexistente;
import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.dominio.model.LikeDislike;
import com.tallerwebi.dominio.model.Resenia;
import com.tallerwebi.dominio.model.Usuario;

import java.util.List;

public interface ServicioResenia {
    void guardarResenia(Usuario usuario, Libro libro, Integer puntuacion, String descripcion);

    Resenia obtenerReseniaPorId(Long id) throws ReseniaInexistente;

    List<Resenia> obtenerReseniasDeOtrosUsuarios(Long userId);

    Double calcularPromedioPuntuacion(Long idLibro);

    Resenia obtenerReseniaDelUsuario(Long userId, Long idLibro);

    void reaccionar(Long idUsuario, Long idResenia, boolean esLike);

    Integer obtenerCantidadLikes(Long idResenia);

    Integer obtenerCantidadDislikes(Long idResenia);

    LikeDislike obtenerReaccionUsuario(Long idResenia, Long userId);
}
