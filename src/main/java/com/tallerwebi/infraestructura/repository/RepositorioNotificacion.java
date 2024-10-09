package com.tallerwebi.infraestructura.repository;

import com.tallerwebi.dominio.model.Notificacion;

public interface RepositorioNotificacion {
    Notificacion encontrarNotificacionPorId(Long id);
    void guardar(Notificacion notificacion);
}
