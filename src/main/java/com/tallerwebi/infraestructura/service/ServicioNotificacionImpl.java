package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.model.Notificacion;
import com.tallerwebi.dominio.model.TipoNotificacion;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.model.UsuarioNotificacion;
import com.tallerwebi.dominio.repository.RepositorioUsuario;
import com.tallerwebi.infraestructura.repository.RepositorioNotificacion;
import com.tallerwebi.infraestructura.repository.RepositorioTipoNotificacion;
import com.tallerwebi.infraestructura.repository.RepositorioUsuarioNotificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class ServicioNotificacionImpl implements ServicioNotificacion{
    private final RepositorioNotificacion repositorioNotificacion;
    private final RepositorioTipoNotificacion repositorioTipoNotificacion;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioUsuarioNotificacion repositorioUsuarioNotificacion;

    @Autowired
    public ServicioNotificacionImpl(RepositorioNotificacion repositorioNotificacion, RepositorioTipoNotificacion repositorioTipoNotificacion, RepositorioUsuario repositorioUsuario, RepositorioUsuarioNotificacion repositorioUsuarioNotificacion) {
        this.repositorioNotificacion = repositorioNotificacion;
        this.repositorioTipoNotificacion = repositorioTipoNotificacion;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioUsuarioNotificacion = repositorioUsuarioNotificacion;
    }

    @Override
    public void crearNotificacion(Long userId, Long tipoNotificacion, String mensaje) throws Exception {
        try {
            Usuario usuario = repositorioUsuario.buscarUsuarioPorId(userId);

            if (usuario == null) {
                throw new Exception ("No existe usuario con ID " + userId);
            }
            Notificacion notificacion = new Notificacion();
            TipoNotificacion notifyType = repositorioTipoNotificacion.encontrarTipoNotificacionPorId(tipoNotificacion);
            if (notifyType == null) {
                throw new Exception("No se encontró tipo de notificación con el ID " + tipoNotificacion);
            }
            notificacion.setMensaje(mensaje);
            notificacion.setTipo(notifyType);
            notificacion.setFechaCreacion(new Date());
            repositorioNotificacion.guardar(notificacion);


            UsuarioNotificacion usuarioNotificacion = new UsuarioNotificacion();
            usuarioNotificacion.setUsuario(usuario);
            usuarioNotificacion.setNotificacion(notificacion);
            usuarioNotificacion.setLeida(false);
            usuarioNotificacion.setFechaRecibida(new Date());

            repositorioUsuarioNotificacion.guardar(usuarioNotificacion);
        }catch (Exception error){
            throw new Exception(error.getMessage());
        }
    }
}
