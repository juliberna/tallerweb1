package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.Usuario;

public interface RepositorioUsuario {

    Usuario buscarPorEmail(String email);

    void guardar(Usuario usuario);

    Usuario buscarPorEmailPass(String email, String password);

    void modificar(Usuario usuario);

    void guardarTokenDeRecuperacion(Usuario usuario, String token);
}

