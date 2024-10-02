package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.infraestructura.service.ServicioHome;

import com.tallerwebi.presentacion.controller.ControladorHome;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class ControladorHomeTest {

    ServicioHome servicioHome = mock(ServicioHome.class);
    ControladorHome controladorInicio = new ControladorHome(servicioHome);

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
