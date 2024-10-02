package com.tallerwebi.integracion;

import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.repository.RepositorioUsuario;
import com.tallerwebi.infraestructura.service.ServicioSolicitudesAmistadImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ServicioSolicitudesAmistadTest {

    @Mock
    private RepositorioUsuario usuarioRepositorio;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private ServicioSolicitudesAmistadImpl servicioSolicitudesAmistad;

    @Before
    public void setup() {
        usuarioRepositorio = mock(RepositorioUsuario.class);
        servicioSolicitudesAmistad = new ServicioSolicitudesAmistadImpl(usuarioRepositorio);
    }

    @Test
    public void deberiaAgregarAmigoCorrectamente() throws Exception {
        Long id1 = 100L;
        Usuario usuario1 = new Usuario();
        usuario1.setId(id1);

        Long id2 = 101L;
        Usuario usuario2 = new Usuario();
        usuario2.setId(id2);

        when(usuarioRepositorio.buscarUsuarioPorId(id1)).thenReturn(usuario1);
        when(usuarioRepositorio.buscarUsuarioPorId(id2)).thenReturn(usuario2);

        servicioSolicitudesAmistad.agregarAmigo(id1, id2);

        assertTrue(usuario1.getAmigos().contains(usuario2));

        verify(usuarioRepositorio).guardarUsuario(usuario1);
    }

    @Test
    public void deberiaLanzarExcepcionCuandoUsuarioNoExiste() throws Exception {
        Long id1 = 100L;
        Long id2 = 101L;
        Usuario usuario2 = new Usuario();
        usuario2.setId(id2);

        when(usuarioRepositorio.buscarUsuarioPorId(id1)).thenReturn(null);
        when(usuarioRepositorio.buscarUsuarioPorId(id2)).thenReturn(usuario2);

        thrown.expect(Exception.class);
        thrown.expectMessage("Usuario o amigo no encontrado");

        servicioSolicitudesAmistad.agregarAmigo(id1, id2);

        verify(usuarioRepositorio, never()).guardarUsuario(any(Usuario.class));
    }

    @Test
    public void deberiaLanzarExcepcionCuandoAmigoNoExiste() throws Exception {
        Long id1 = 100L;
        Long id2 = 101L;
        Usuario usuario1 = new Usuario();
        usuario1.setId(id1);

        when(usuarioRepositorio.buscarUsuarioPorId(id1)).thenReturn(usuario1);
        when(usuarioRepositorio.buscarUsuarioPorId(id2)).thenReturn(null);

        thrown.expect(Exception.class);
        thrown.expectMessage("Usuario o amigo no encontrado");

        servicioSolicitudesAmistad.agregarAmigo(id1, id2);

        verify(usuarioRepositorio, never()).guardarUsuario(any(Usuario.class));
    }

    @Test
    public void deberiaLanzarExcepcionCuandoAmbosUsuariosNoExisten() throws Exception {
        Long id1 = 100L;
        Long id2 = 101L;

        when(usuarioRepositorio.buscarUsuarioPorId(id1)).thenReturn(null);
        when(usuarioRepositorio.buscarUsuarioPorId(id2)).thenReturn(null);

        thrown.expect(Exception.class);
        thrown.expectMessage("Usuario o amigo no encontrado");

        servicioSolicitudesAmistad.agregarAmigo(id1, id2);

        verify(usuarioRepositorio, never()).guardarUsuario(any(Usuario.class));
    }
}
