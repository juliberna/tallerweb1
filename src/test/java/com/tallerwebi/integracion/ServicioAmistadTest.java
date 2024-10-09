package com.tallerwebi.integracion;

import com.tallerwebi.dominio.model.Amistad;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.repository.RepositorioUsuario;
import com.tallerwebi.infraestructura.repository.RepositorioAmistad;
import com.tallerwebi.infraestructura.service.ServicioAmistadImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ServicioAmistadTest {

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @Mock
    private RepositorioAmistad repositorioAmistad;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private ServicioAmistadImpl servicioAmistad;

    @Before
    public void setup() {
        repositorioUsuario = mock(RepositorioUsuario.class);
        repositorioAmistad = mock(RepositorioAmistad.class);
        servicioAmistad = new ServicioAmistadImpl(repositorioUsuario, repositorioAmistad);
    }

    @Test
    public void deberiaEnviarSolicitudDeAmistadCorrectamente() throws Exception {
        Long id1 = 100L;
        Usuario usuario1 = new Usuario();
        usuario1.setId(id1);
        usuario1.setEmail("usuario1@mail.com");

        Long id2 = 101L;
        Usuario usuario2 = new Usuario();
        usuario2.setId(id2);
        usuario2.setEmail("usuario2@mail.com");

        when(repositorioUsuario.buscarUsuarioPorId(id1)).thenReturn(usuario1);
        when(repositorioUsuario.buscarUsuarioPorId(id2)).thenReturn(usuario2);

        Amistad solicitud = new Amistad();
        solicitud.setUsuario(usuario1);
        solicitud.setAmigo(usuario2);
        solicitud.setFechaSolicitud(new Date());
        solicitud.setEstado("pendiente");

        when(repositorioAmistad.guardar(any(Amistad.class))).thenReturn(true);

        boolean resultado = servicioAmistad.enviarSolicitudDeAmistad(id1, id2);

        assertTrue(resultado);
        verify(repositorioAmistad).guardar(any(Amistad.class));
    }

    @Test
    public void deberiaLanzarExcepcionCuandoUsuarioNoExiste() throws Exception {
        Long id1 = 100L;
        Long id2 = 101L;
        Usuario usuario2 = new Usuario();
        usuario2.setId(id2);
        usuario2.setEmail("usuario2@mail.com");

        when(repositorioUsuario.buscarUsuarioPorId(id1)).thenReturn(null);
        when(repositorioUsuario.buscarUsuarioPorId(id2)).thenReturn(usuario2);

        thrown.expect(Exception.class);
        thrown.expectMessage("Usuario o amigo no encontrado");

        servicioAmistad.enviarSolicitudDeAmistad(id1, id2);

        verify(repositorioAmistad, never()).guardar(any(Amistad.class));
    }

    @Test
    public void deberiaLanzarExcepcionCuandoAmigoNoExiste() throws Exception {
        Long id1 = 100L;
        Usuario usuario1 = new Usuario();
        usuario1.setId(id1);
        usuario1.setEmail("usuario1@mail.com");

        Long id2 = 101L;

        when(repositorioUsuario.buscarUsuarioPorId(id1)).thenReturn(usuario1);
        when(repositorioUsuario.buscarUsuarioPorId(id2)).thenReturn(null);

        thrown.expect(Exception.class);
        thrown.expectMessage("Usuario o amigo no encontrado");

        servicioAmistad.enviarSolicitudDeAmistad(id1, id2);

        verify(repositorioAmistad, never()).guardar(any(Amistad.class));
    }

    @Test
    public void deberiaLanzarExcepcionCuandoAmbosUsuariosNoExisten() throws Exception {
        Long id1 = 100L;
        Long id2 = 101L;

        when(repositorioUsuario.buscarUsuarioPorId(id1)).thenReturn(null);
        when(repositorioUsuario.buscarUsuarioPorId(id2)).thenReturn(null);

        thrown.expect(Exception.class);
        thrown.expectMessage("Usuario o amigo no encontrado");

        servicioAmistad.enviarSolicitudDeAmistad(id1, id2);

        verify(repositorioAmistad, never()).guardar(any(Amistad.class));
    }
}
