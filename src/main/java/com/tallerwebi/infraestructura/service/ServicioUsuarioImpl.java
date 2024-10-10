package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.repository.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario {

    RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) throws UsuarioInexistente {
        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(id);
        if(usuario == null)
            throw new UsuarioInexistente();

        return usuario;
    }

    @Override
    public void actualizarUsuario(Long idUsuarioActual, Usuario usuarioActualizado) throws UsuarioInexistente {
        Usuario usuarioExistente = repositorioUsuario.buscarUsuarioPorId(idUsuarioActual);

        if(usuarioExistente == null)
            throw new UsuarioInexistente();

        // Actualizar los campos del usuario existente
        usuarioExistente.setNombreUsuario(usuarioActualizado.getNombreUsuario());
        usuarioExistente.setEmail(usuarioActualizado.getEmail());
        usuarioExistente.setMeta(usuarioActualizado.getMeta());
        usuarioExistente.setBiografia(usuarioActualizado.getBiografia());
        usuarioExistente.setImagenUrl(usuarioActualizado.getImagenUrl());

        // Guardar el usuario actualizado en la base de datos
        try {
            repositorioUsuario.guardarUsuario(usuarioExistente);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existeNombreUsuario(String nombre, Long idUsuario) {
        Usuario usuario = repositorioUsuario.buscarPorNombreUsuario(nombre);
        // Verifica si existe el usuario y que no sea el actual
        return usuario != null && !(Objects.equals(usuario.getId(), idUsuario));
    }

    @Override
    public boolean existeEmailUsuario(String email, Long idUsuario) {
        Usuario usuario = repositorioUsuario.buscarPorEmail(email);
        return usuario != null && !(Objects.equals(usuario.getId(), idUsuario));
    }
}
