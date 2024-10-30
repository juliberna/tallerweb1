package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.Logro;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.model.UsuarioLogro;

import java.util.List;

public interface RepositorioUsuarioLogro {
    void guardar(UsuarioLogro usuario);
    UsuarioLogro buscarUsuarioLogro(Usuario usuario, Logro logro);
    List<UsuarioLogro> obtenerLogrosPorUsuario(Usuario usuario);
    List<UsuarioLogro> obtenerTodos();
}
