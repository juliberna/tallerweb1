package com.tallerwebi.presentacion;

import com.tallerwebi.presentacion.controller.ControladorPago;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ControladorPagoTest {

    @Mock
    private HttpSession session;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private ControladorPago controladorPago;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPagoExitoso() {
        // Datos simulados de la solicitud
        String paymentId = "12345";
        String status = "approved";
        String externalReference = "1";  // ID del plan

        // Ejecutar el controlador
        String viewName = controladorPago.pagoExitoso(paymentId, status, externalReference, session);

        // Verificar el comportamiento
        verify(session, times(1)).setAttribute("mensajeEstadoPago", "Pago exitoso. ID de pago: 12345, Estado: approved");
        assertEquals("redirect:/planes/confirmarActualizar/1", viewName);
    }

    @Test
    void testPagoError() {
        // Datos simulados de la solicitud
        String paymentId = "12345";
        String status = "error";
        String externalReference = "1";

        // Ejecutar el controlador
        String viewName = controladorPago.pagoError(paymentId, status, externalReference, redirectAttributes);

        // Verificar el comportamiento
        verify(redirectAttributes, times(1)).addFlashAttribute("mensajeEstadoPago", "Error en el pago. ID de pago: 12345, Estado: error");
        assertEquals("redirect:/planes/mostrar", viewName);
    }

    @Test
    void testPagoPendiente() {
        // Datos simulados de la solicitud
        String paymentId = "12345";
        String status = "pending";
        String externalReference = "1";

        // Ejecutar el controlador
        String viewName = controladorPago.pagoPendiente(paymentId, status, externalReference, redirectAttributes);

        // Verificar el comportamiento
        verify(redirectAttributes, times(1)).addFlashAttribute("mensajeEstadoPago", "Pago pendiente. ID de pago: 12345, Estado: pending");
        assertEquals("redirect:/planes/mostrar", viewName);
    }
}
