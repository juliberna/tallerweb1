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

    private void givenExistenLibros() {
    }

    private Set<Libro> whenBuscarLibros(String query) throws QueryVacia, ListaVacia {
        return servicioLibro.buscar(query);
    }

    private void thenLaBusquedaEsExitosa(Set<Libro> librosObtenidos) {
        assertThat(librosObtenidos, notNullValue());
    }

}
