package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import com.tallerwebi.dominio.model.Usuario;

public interface ServicioUsuario {
    Usuario buscarUsuarioPorId(Long id) throws UsuarioInexistente;
}
