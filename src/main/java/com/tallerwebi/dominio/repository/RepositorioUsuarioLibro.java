package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.UsuarioLibro;

public interface RepositorioUsuarioLibro {
    UsuarioLibro encontrarUsuarioIdYLibroId(Long usuarioId, Long libroId);
    void guardar(UsuarioLibro usuarioLibro);
}
