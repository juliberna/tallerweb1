package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.model.UsuarioLibro;

public interface ServicioUsuarioLibro {
    //void agregarLibroUsuario(Long idUsuario, Long idLibro);
    //void cambiarEstadoDeLectura(Long idUsuario, Long idLibro, String estado);
    UsuarioLibro obtenerUsuarioLibro(Long usuarioId, Long libroId);
    void guardarUsuarioLibro(UsuarioLibro usuarioLibro);
    void crearOActualizarUsuarioLibro(Long usuarioId, Long libroId, String estadoDeLectura, Integer puntuacion, String reseña);
}
