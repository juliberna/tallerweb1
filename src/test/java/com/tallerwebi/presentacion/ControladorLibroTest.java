package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.infraestructura.service.ServicioLibro;
import com.tallerwebi.dominio.excepcion.ListaVacia;
import com.tallerwebi.dominio.excepcion.QueryVacia;
import com.tallerwebi.presentacion.controller.ControladorLibro;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorLibroTest {

    ServicioLibro servicioLibro = mock(ServicioLibro.class);
    ControladorLibro controladorLibro = new ControladorLibro(servicioLibro);

    @Test
    public void siLaQueryDeBusquedaContieneTextoLaBusquedaEsExitosa() throws ListaVacia, QueryVacia {
        //given
        givenExistenLibros();
        when(servicioLibro.buscar("amor")).thenReturn(new HashSet<>(
                Set.of(new Libro())));
        //when
        ModelAndView mav = whenBuscarLibro("amor");
        //then
        thenLaBusquedaEsExitosa(mav);
    }

    @Test
    public void siLaQueryDeBusquedaEstaVaciaMuestraError() throws ListaVacia, QueryVacia {
        givenExistenLibros();
        doThrow(QueryVacia.class).when(servicioLibro).buscar("");

        ModelAndView mav = whenBuscarLibro("");

        thenLaBusquedaFalla(mav, "El campo de busqueda esta vacio");
    }

    @Test
    public void siLaListaDeLibrosObtenidosEstaVaciaMuestraError() throws ListaVacia, QueryVacia {
        givenExistenLibros();
        doThrow(ListaVacia.class).when(servicioLibro).buscar("*-,vasda");

        ModelAndView mav = whenBuscarLibro("*-,vasda");

        thenLaBusquedaFalla(mav, "No se encontraron libros que coincidan con la busqueda");
    }

    private void givenExistenLibros() {
    }

    private ModelAndView whenBuscarLibro(String query) {
        return controladorLibro.buscarLibros(query);
    }

    private void thenLaBusquedaEsExitosa(ModelAndView mav) {
        assertThat(mav.getViewName(), equalToIgnoringCase("resultados_busqueda"));
    }

    private void thenLaBusquedaFalla(ModelAndView mav, String mensajeError) {
        assertThat(mav.getModel().get("error").toString(), equalToIgnoringCase(mensajeError));
    }
}
