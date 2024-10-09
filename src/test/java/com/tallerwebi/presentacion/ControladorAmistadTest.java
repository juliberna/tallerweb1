package com.tallerwebi.presentacion;

import com.tallerwebi.infraestructura.service.ServicioAmistad;
import com.tallerwebi.infraestructura.service.ServicioNotificacion;
import com.tallerwebi.presentacion.controller.ControladorAmistad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ControladorAmistadTest {

    private ServicioAmistad servicioAmistad;
    private ServicioNotificacion servicioNotificacion;
    private ControladorAmistad controladorAmistad;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;

    @BeforeEach
    public void setUp() {
        servicioAmistad = mock(ServicioAmistad.class);
        servicioNotificacion = mock(ServicioNotificacion.class);
        controladorAmistad = new ControladorAmistad(servicioAmistad, servicioNotificacion);

        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);

        ServletRequestAttributes attr = new ServletRequestAttributes(requestMock);
        RequestContextHolder.setRequestAttributes(attr);
    }

    @Test
    public void siLaSolicitudDeAmistadEsExitosaDevuelveVistaCorrecta() throws Exception {
        Long userId = 70L;
        Long friendId = 20L;
        String username = "usuarioEjemplo";

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("USERID")).thenReturn(userId);
        when(sessionMock.getAttribute("USERNAME")).thenReturn(username);

        when(servicioAmistad.enviarSolicitudDeAmistad(userId, friendId)).thenReturn(true);

        String vista = controladorAmistad.enviarSolicitudAmistad(friendId);

        verify(servicioAmistad).enviarSolicitudDeAmistad(userId, friendId);
        verify(servicioNotificacion).crearNotificacion(friendId, 2L, "Has recibido una solicitud de Amistad de " + username);
        assertThat(vista, equalTo("amigoAgregadoCorrectamente"));
    }

    @Test
    public void siLaSolicitudDeAmistadFallaDevuelveMensajeDeError() throws Exception {
        Long userId = 70L;
        Long friendId = 20L;

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("USERID")).thenReturn(userId);

        when(servicioAmistad.enviarSolicitudDeAmistad(userId, friendId)).thenThrow(new Exception("Error al enviar solicitud"));

        String vista = controladorAmistad.enviarSolicitudAmistad(friendId);

        verify(servicioAmistad).enviarSolicitudDeAmistad(userId, friendId);
        assertThat(vista, equalTo("Error al enviar solicitud"));
    }
}
