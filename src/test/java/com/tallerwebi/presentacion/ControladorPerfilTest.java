package com.tallerwebi.presentacion;


import com.tallerwebi.infraestructura.service.ServicioLibro;
import com.tallerwebi.presentacion.controller.ControladorPerfil;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.mock;

public class ControladorPerfilTest {

    ServicioLibro servicioLibro = mock(ServicioLibro.class);
    ControladorPerfil controladorPerfil = new ControladorPerfil(servicioLibro);

    //TODO si el usuario no esta registrado mostrar pagina de error


    @Test
    public void siElUsuarioTienePerfilMostrarlo() throws Exception {
        givenExisteUnUsuarioRegistrado();

        ModelAndView mav = controladorPerfil.mostrarPerfil();

        thenSeMuestraElPerfil(mav);
    }

    @Test
    public void elUsuarioPuedeCambiarLaCategoriaDeLaEstanteria() {
        givenExisteUnUsuarioRegistrado();

        ModelAndView mav = controladorPerfil.cambiarCategoria("Quiero leer");

        thenSeMuestraElPerfil(mav);
        assertThat(mav.getModel().get("categoriaActual").toString(), equalToIgnoringCase("Quiero leer"));
    }

    private void givenExisteUnUsuarioRegistrado() {}

    private void thenSeMuestraElPerfil(ModelAndView mav) {
        assertThat(mav.getViewName(), equalToIgnoringCase("perfil"));
    }
}
