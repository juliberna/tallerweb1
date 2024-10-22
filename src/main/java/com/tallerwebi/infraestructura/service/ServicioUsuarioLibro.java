package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.excepcion.ListaVacia;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.model.UsuarioLibro;

import java.util.List;

public interface ServicioUsuarioLibro {
    UsuarioLibro obtenerUsuarioLibro(Long usuarioId, Long libroId);

    void guardarUsuarioLibro(UsuarioLibro usuarioLibro);

    void crearOActualizarUsuarioLibro(Long usuarioId, Long libroId, String estadoDeLectura, Integer puntuacion, String reseña);

    List<UsuarioLibro> buscarPorEstadoDeLectura(String estadoDeLectura, Usuario usuario) throws ListaVacia;

    Double calcularPromedioDePuntuacion(Long libroId);

    List<UsuarioLibro> buscarLibrosLeidosPorAño(Integer anio, Usuario usuario) throws ListaVacia;
}
