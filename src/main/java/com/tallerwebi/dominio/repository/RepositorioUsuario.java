package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.Usuario;

import java.time.LocalDate;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);
    void guardar(String email, String password, String nombreUsuario, String nombre, LocalDate fechaNacimiento);
    void modificar(Usuario usuario);
    Usuario buscar(String email);

    void guardarTokenDeRecuperacion(Usuario usuario, String token);
}

