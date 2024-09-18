package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.excepcion.ListaDeReviewsVacias;
import com.tallerwebi.dominio.excepcion.QueryVacia;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.infraestructura.service.ServicioInicio;

import com.tallerwebi.presentacion.controller.ControladorInicio;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class ControladorInicioTest {

    ServicioInicio servicioInicio = mock(ServicioInicio.class);
    ControladorInicio controladorInicio = new ControladorInicio(servicioInicio);

    @Test
    public void queSeMuestreElNombreDelUsuarioCorrectamente() {

        Usuario usuario = new Usuario();
        usuario.setEmail("hola123@gmail.com");
        usuario.setPassword("hola123");





    }
   @Test
    public void queNoSePuedaMostrarUnaListaDeReviewsVacia() {

   }

}
