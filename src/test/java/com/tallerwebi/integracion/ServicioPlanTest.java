package com.tallerwebi.integracion;

import com.tallerwebi.dominio.excepcion.PaginasExcedidas;
import com.tallerwebi.dominio.excepcion.PlanNoEncontrado;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import com.tallerwebi.dominio.model.Plan;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.repository.RepositorioPlan;
import com.tallerwebi.dominio.repository.RepositorioUsuario;
import com.tallerwebi.infraestructura.service.ServicioPlanImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import java.util.Date;
import java.util.List;


import static org.mockito.Mockito.*;

public class ServicioPlanTest {

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @Mock
    private RepositorioPlan repositorioPlan;

    @InjectMocks
    private ServicioPlanImpl servicioPlan;

    private Usuario usuarioMock;
    private Plan planMock;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioMock = mock(Usuario.class);
        planMock = mock(Plan.class);
    }

    @Test
    public void queSePuedaActualizarUnPlanDeUnUsuario() throws Exception {
        Long idPlan = 1L;
        Long idUsuario = 1L;

        when(repositorioPlan.buscarPlanPorId(idPlan)).thenReturn(planMock);
        when(repositorioUsuario.buscarUsuarioPorId(idUsuario)).thenReturn(usuarioMock);
        when(planMock.getNombre()).thenReturn("PLATA");


        servicioPlan.actualizarPlanDelUsuario(idPlan, idUsuario);

        verify(usuarioMock).setPlan(planMock);
        verify(usuarioMock).setFecha_plan_adquirido(any(Date.class));
        verify(usuarioMock).setFecha_plan_venc(any(Date.class));
        verify(repositorioUsuario).guardarUsuario(usuarioMock);
    }

    @Test(expected = UsuarioInexistente.class)
    public void queAlQuererActualizarUnPlanSinUsuarioQueArrojeExcepcion() throws UsuarioInexistente {
        Long idPlan = 1L;
        Long idUsuario = 1L;

        when(repositorioPlan.buscarPlanPorId(idPlan)).thenReturn(planMock);
        when(repositorioUsuario.buscarUsuarioPorId(idUsuario)).thenReturn(null);

        servicioPlan.actualizarPlanDelUsuario(idPlan, idUsuario);
    }

    @Test(expected = PlanNoEncontrado.class)
    public void queAlQuererActualizarUnPlanSinPlanExistenteQueArrojeExcepcion() throws Exception {
        Long idPlan = 1L;
        Long idUsuario = 1L;

        when(repositorioPlan.buscarPlanPorId(idPlan)).thenReturn(null);
        when(repositorioUsuario.buscarUsuarioPorId(idUsuario)).thenReturn(usuarioMock);

        servicioPlan.actualizarPlanDelUsuario(idPlan, idUsuario);
    }

    @Test
    public void queSePuedaVerificarPlanes() {

        Usuario usuarioMock2 = mock(Usuario.class);
        when(usuarioMock.getFecha_plan_venc()).thenReturn(new Date(System.currentTimeMillis() - 100000)); // Fecha pasada
        when(usuarioMock2.getFecha_plan_venc()).thenReturn(new Date(System.currentTimeMillis() + 100000)); // Fecha futura
        when(repositorioUsuario.obtenerUsuarios()).thenReturn(List.of(usuarioMock, usuarioMock2));
        when(repositorioPlan.buscarPlanBronce()).thenReturn(planMock);


        servicioPlan.verificarPlanes();

        // Verificar que el plan del usuario con fecha vencida se haya actualizado a BRONCE
        verify(usuarioMock).setPlan(planMock);
        verify(repositorioUsuario).guardarUsuario(usuarioMock);
    }
}
