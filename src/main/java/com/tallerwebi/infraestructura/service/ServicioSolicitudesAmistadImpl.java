package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.repository.RepositorioUsuario;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioSolicitudesAmistadImpl implements ServicioSolicitudesAmistad{

    private final RepositorioUsuario repositorioUsuario;

    public ServicioSolicitudesAmistadImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public void agregarAmigo(Long userId, Long friendId) throws Exception {
        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(userId);
        Usuario friend = repositorioUsuario.buscarUsuarioPorId(friendId);

        if (usuario == null || friend == null) {
            throw new Exception("Usuario o amigo no encontrado");

        } else {
            usuario.agregarAmigo(friend);
            repositorioUsuario.guardarUsuario(usuario);
        }
    }
}
