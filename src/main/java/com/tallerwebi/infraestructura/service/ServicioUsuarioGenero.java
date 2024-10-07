package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.model.UsuarioGenero;
import com.tallerwebi.dominio.model.UsuarioLibro;

public interface ServicioUsuarioGenero {
    UsuarioGenero obtenerUsuarioGenero(Long usuarioId, Long generoId);
    void guardarUsuarioGenero(UsuarioGenero usuarioGenero);
    void crearOActualizarUsuarioGenero(Long usuarioId, Long generoId);
}
