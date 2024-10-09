package com.tallerwebi.infraestructura.service;

public interface ServicioNotificacion
{
    void crearNotificacion(Long userId, Long tipoNotificacion, String mensaje) throws Exception;
}
