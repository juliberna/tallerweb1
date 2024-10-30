package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.excepcion.ListaVacia;
import com.tallerwebi.dominio.model.Logro;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.model.UsuarioLogro;

import java.util.List;

public interface ServicioUsuarioLogro {
    List<UsuarioLogro> obtenerLogrosPorUsuario(Usuario usuario) throws ListaVacia;
    void verificarYAsignarLogros(Usuario usuario) throws ListaVacia;
    List<Logro> obtenerLogrosPorCompletar(Usuario usuario);
}
