package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.UsuarioGenero;

public interface RepositorioUsuarioGenero {
    UsuarioGenero encontrarUsuarioIdYGeneroId(Long usuarioId, Long generoId);
    void guardar(UsuarioGenero usuarioGenero);

}
