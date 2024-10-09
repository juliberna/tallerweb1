package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.model.UsuarioLibro;
import com.tallerwebi.dominio.repository.RepositorioLibro;
import com.tallerwebi.dominio.repository.RepositorioUsuarioLibro;
import com.tallerwebi.infraestructura.service.ServicioUsuarioLibroImpl;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.is;

public class ServicioUsuarioLibroTest {

    private RepositorioUsuarioLibro repositorioUsuarioLibro = Mockito.mock(RepositorioUsuarioLibro.class);
    private RepositorioLibro repositorioLibro = Mockito.mock(RepositorioLibro.class);

    private ServicioUsuarioLibroImpl servicioUsuarioLibro = new ServicioUsuarioLibroImpl(repositorioUsuarioLibro, null, repositorioLibro);

    @Test
    public void siElLibroTienePuntuacionesCalculaElPromedioCorrectamente() {
        // Dado
        Long libroId = 1L;
        List<UsuarioLibro> usuariosLibro = new ArrayList<>();

        UsuarioLibro usuarioLibro1 = new UsuarioLibro();
        usuarioLibro1.setPuntuacion(4);
        usuariosLibro.add(usuarioLibro1);

        UsuarioLibro usuarioLibro2 = new UsuarioLibro();
        usuarioLibro2.setPuntuacion(5);
        usuariosLibro.add(usuarioLibro2);

        UsuarioLibro usuarioLibro3 = new UsuarioLibro();
        usuarioLibro3.setPuntuacion(3);
        usuariosLibro.add(usuarioLibro3);

        when(repositorioUsuarioLibro.buscarLibroPorId(libroId)).thenReturn(usuariosLibro);

        // Cuando
        Double promedio = servicioUsuarioLibro.calcularPromedioDePuntuacion(libroId);

        // Entonces
        assertThat(promedio, equalTo(4.0));  // Promedio: (4+5+3)/3 = 4.0
    }

    @Test
    public void siNoHayPuntuacionesDevuelveCero() {
        // Dado
        Long libroId = 1L;
        List<UsuarioLibro> usuariosLibro = new ArrayList<>();

        when(repositorioUsuarioLibro.buscarLibroPorId(libroId)).thenReturn(usuariosLibro);

        // Cuando
        Double promedio = servicioUsuarioLibro.calcularPromedioDePuntuacion(libroId);

        // Entonces
        assertThat(promedio, is(equalTo(0.0)));  // Esperamos que devuelva 0.0
    }

    @Test
    public void siTodosLosUsuariosTienenPuntuacionNulaDevuelveCero() {
        // Dado
        Long libroId = 1L;
        List<UsuarioLibro> usuariosLibro = new ArrayList<>();

        UsuarioLibro usuarioLibro1 = new UsuarioLibro();
        usuarioLibro1.setPuntuacion(null);  // Puntuación nula
        usuariosLibro.add(usuarioLibro1);

        UsuarioLibro usuarioLibro2 = new UsuarioLibro();
        usuarioLibro2.setPuntuacion(null);  // Puntuación nula
        usuariosLibro.add(usuarioLibro2);

        when(repositorioUsuarioLibro.buscarLibroPorId(libroId)).thenReturn(usuariosLibro);

        // Cuando
        Double promedio = servicioUsuarioLibro.calcularPromedioDePuntuacion(libroId);

        // Entonces
        assertThat(promedio, is(equalTo(0.0)));  // Esperamos que devuelva 0.0
    }
}
