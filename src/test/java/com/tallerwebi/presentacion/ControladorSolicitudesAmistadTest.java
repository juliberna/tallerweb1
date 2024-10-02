package com.tallerwebi.presentacion;

import com.tallerwebi.infraestructura.service.ServicioSolicitudesAmistad;
import com.tallerwebi.presentacion.controller.ControladorSolicitudesAmistad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ControladorSolicitudesAmistadTest {

    private ControladorSolicitudesAmistad controladorSolicitudesAmistad;
    private ServicioSolicitudesAmistad servicioSolicitudesAmistadMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;

    @BeforeEach
    public void setUp() {
        servicioSolicitudesAmistadMock = mock(ServicioSolicitudesAmistad.class);
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        controladorSolicitudesAmistad = new ControladorSolicitudesAmistad(servicioSolicitudesAmistadMock);

        ServletRequestAttributes attr = new ServletRequestAttributes(requestMock);
        RequestContextHolder.setRequestAttributes(attr);
    }

    @Test
    public void agregarAmigoExitosoDeberiaDevolverMensajeDeExito() throws Exception {
        Long userId = 70L;
        Long friendId = 77L;
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("USERID")).thenReturn(userId);

        doNothing().when(servicioSolicitudesAmistadMock).agregarAmigo(userId, friendId);
        doNothing().when(servicioSolicitudesAmistadMock).agregarAmigo(friendId, userId);

        String resultado = controladorSolicitudesAmistad.agregarAmigo(friendId);

        assertEquals("Amigo agregado correctamente", resultado);
        verify(servicioSolicitudesAmistadMock, times(1)).agregarAmigo(userId, friendId);
        verify(servicioSolicitudesAmistadMock, times(1)).agregarAmigo(friendId, userId);
    }

    @Test
    public void agregarAmigoConErrorDeberiaDevolverMensajeDeError() throws Exception {
        Long userId = 1L;
        Long friendId = 2L;
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("USERID")).thenReturn(userId);

        doThrow(new Exception("Usuario o amigo no encontrado")).when(servicioSolicitudesAmistadMock).agregarAmigo(userId, friendId);

        String resultado = controladorSolicitudesAmistad.agregarAmigo(friendId);

        assertEquals("Usuario o amigo no encontrado", resultado);
        verify(servicioSolicitudesAmistadMock, times(1)).agregarAmigo(userId, friendId);
        verify(servicioSolicitudesAmistadMock, times(0)).agregarAmigo(friendId, userId);
    }
}
