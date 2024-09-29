package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.excepcion.ListaVacia;
import com.tallerwebi.dominio.excepcion.QueryVacia;
import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.dominio.repository.RepositorioLibro;
import com.tallerwebi.infraestructura.service.ServicioLibroImpl;
import com.tallerwebi.infraestructura.service.ServicioLibro;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ServicioLibroTest {

    RepositorioLibro repositorioLibro = Mockito.mock(RepositorioLibro.class);
    ServicioLibro servicioLibro = new ServicioLibroImpl(repositorioLibro);

    @Test
    public void siLaQueryDeBusquedaContieneTextoLaBusquedaEsExitosa() throws QueryVacia, ListaVacia {
        //given
        givenExistenLibros();
        when(repositorioLibro.buscar("amor")).thenReturn(new ArrayList<>(
                List.of(new Libro())));
        //when
        Set<Libro>librosObtenidos = whenBuscarLibros("amor");
        //then
        thenLaBusquedaEsExitosa(librosObtenidos);
    }

    @Test
    public void siLaQueryDeBusquedaEstaVaciaArrojaExcepcion() {
        givenExistenLibros();
        String queryVacia = "";
        assertThrows(QueryVacia.class, () -> whenBuscarLibros(queryVacia));
    }

    @Test
    public void siLaListaDeLibrosObtenidosEstaVaciaArrojaExcepcion() {
        givenExistenLibros();
        when(repositorioLibro.buscar("error")).thenReturn(new ArrayList<>());
        assertThrows(ListaVacia.class,() -> whenBuscarLibros("error"));
    }

    @Test
    public void siElEstadoDeLecturaExisteDevuelveLosLibrosQueCoincidan() throws ListaVacia {
        givenExistenLibrosConEstadosDeLectura();

        when(repositorioLibro.buscarPorEstadoDeLectura("Quiero leer")).thenReturn(new ArrayList<>(
                List.of(new Libro())));

        List<Libro> librosObtenidos = whenBuscarLibrosPorEstadoDeLectura("Quiero leer");

        thenLaBusquedaEsExitosa(librosObtenidos);
    }

    @Test
    public void siLaListaDeLibrosObtenidosPorEstadoEstaVaciaArrojaExcepcion() {
        givenExistenLibrosConEstadosDeLectura();
        when(repositorioLibro.buscarPorEstadoDeLectura("error")).thenReturn(new ArrayList<>());
        assertThrows(ListaVacia.class, () -> whenBuscarLibrosPorEstadoDeLectura("error"));
    }

    private void givenExistenLibros() {
    }

    private void givenExistenLibrosConEstadosDeLectura() {}

    private Set<Libro> whenBuscarLibros(String query) throws QueryVacia, ListaVacia {
        return servicioLibro.buscar(query);
    }

    private List<Libro> whenBuscarLibrosPorEstadoDeLectura(String estadoLectura) throws ListaVacia {
        return servicioLibro.buscarPorEstadoDeLectura(estadoLectura);
    }

    private void thenLaBusquedaEsExitosa(Set<Libro> librosObtenidos) {
        assertThat(librosObtenidos, notNullValue());
    }

    private void thenLaBusquedaEsExitosa(List<Libro> librosObtenidos) {
        assertThat(librosObtenidos, notNullValue());
    }

}
