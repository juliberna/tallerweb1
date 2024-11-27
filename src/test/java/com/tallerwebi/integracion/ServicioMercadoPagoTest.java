package com.tallerwebi.integracion;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.tallerwebi.dominio.model.Plan;
import com.tallerwebi.infraestructura.service.ServicioMercadoPagoImpl;
import com.tallerwebi.infraestructura.service.ServicioPlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioMercadoPagoTest {

    @Mock
    private ServicioPlan servicioPlan;

    @InjectMocks
    private ServicioMercadoPagoImpl servicioMercadoPago;

    private Plan mockPlan;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurar el mock del plan
        mockPlan = new Plan();
        mockPlan.setId(1L);
        mockPlan.setNombre("Plan Básico");
        mockPlan.setPrecio(1000D);

        // Configurar el servicio plan para que devuelva el plan simulado
        when(servicioPlan.buscarPlanPorId(1L)).thenReturn(mockPlan);
    }

    @Test
    void testCrearPreferencia_Success() throws MPException, MPApiException {
        // Ejecutar el método
        String linkPago = servicioMercadoPago.crearPreferencia(1L);

        // Verificar el comportamiento
        assertNotNull(linkPago);
        assertTrue(linkPago.startsWith("https://www.mercadopago.com.ar/checkout"));
        verify(servicioPlan, times(1)).buscarPlanPorId(1L);
    }

    @Test
    void testCrearPreferencia_PlanNoExistente() throws MPException, MPApiException {
        // Configurar para que el servicio plan retorne null
        when(servicioPlan.buscarPlanPorId(2L)).thenReturn(null);

        // Verificar que lanza una excepción cuando el plan no existe
        MPException thrown = assertThrows(MPException.class, () -> {
            servicioMercadoPago.crearPreferencia(2L);
        });

        assertEquals("Plan no encontrado", thrown.getMessage());
    }
}
