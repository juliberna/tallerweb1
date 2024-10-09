package com.tallerwebi.infraestructura.service;

public interface ServicioAmistad {
    boolean enviarSolicitudDeAmistad(Long userId, Long friendId) throws Exception;
}
