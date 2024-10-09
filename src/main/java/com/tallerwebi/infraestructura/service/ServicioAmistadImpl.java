package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.model.Amistad;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.repository.RepositorioUsuario;
import com.tallerwebi.infraestructura.repository.RepositorioAmistad;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class ServicioAmistadImpl implements ServicioAmistad {
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioAmistad repositorioAmistad;

    public ServicioAmistadImpl(RepositorioUsuario repositorioUsuario, RepositorioAmistad repositorioAmistad) {
        this.repositorioAmistad = repositorioAmistad;
        this.repositorioUsuario = repositorioUsuario;
    }


    @Override
    public boolean enviarSolicitudDeAmistad(Long userId, Long friendId) throws Exception {
        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(userId);
        Usuario friend = repositorioUsuario.buscarUsuarioPorId(friendId);
        try {

            if (usuario == null || friend == null) {
                throw new Exception("Usuario o amigo no encontrado");

            } else {
                Amistad solicitud = new Amistad();
                solicitud.setUsuario(usuario);
                solicitud.setAmigo(friend);
                solicitud.setFechaSolicitud(new Date());
                solicitud.setEstado("pendiente");
                Boolean saved = repositorioAmistad.guardar(solicitud);
                if (saved) {
                    return true;
                } else {
                    return false;
                }
            }

        } catch (Exception error){
            throw new Exception(error.getMessage());

        }
    }
}
