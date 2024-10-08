package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.excepcion.ListaVacia;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.model.UsuarioLibro;
import com.tallerwebi.infraestructura.service.ServicioLibro;
import com.tallerwebi.infraestructura.service.ServicioUsuario;
import com.tallerwebi.infraestructura.service.ServicioUsuarioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorPerfil {

    private ServicioUsuario servicioUsuario;
    private ServicioUsuarioLibro servicioUsuarioLibro;

    @Autowired
    public ControladorPerfil(ServicioUsuarioLibro servicioUsuarioLibro, ServicioUsuario servicioUsuario) {
        this.servicioUsuarioLibro = servicioUsuarioLibro;
        this.servicioUsuario = servicioUsuario;
    }

    @RequestMapping(value = "/perfil/{id}",method = RequestMethod.GET)
    public ModelAndView mostrarPerfil(@PathVariable Long id) {
        ModelMap model = new ModelMap();

        try {
            Usuario usuario = servicioUsuario.buscarUsuarioPorId(id);
            model.addAttribute("usuario", usuario);
            List<UsuarioLibro> libros = servicioUsuarioLibro.buscarPorEstadoDeLectura("Leyendo", usuario);
            model.addAttribute("libros", libros);

        } catch (UsuarioInexistente e) {
            return new ModelAndView("redirect:/login");
        } catch (ListaVacia e) {
            model.addAttribute("error", "No tiene libros con este estado");
        }
        return new ModelAndView("perfil", model);
    }

    @RequestMapping(value = "/perfil/{id}/estanteria")
    public ModelAndView cambiarCategoria(@PathVariable Long id, @RequestParam("estado") String estadoDeLectura) {
        ModelMap model = new ModelMap();

        try {
            Usuario usuario = servicioUsuario.buscarUsuarioPorId(id);
            model.addAttribute("usuario", usuario);
            List<UsuarioLibro> libros = servicioUsuarioLibro.buscarPorEstadoDeLectura(estadoDeLectura, usuario);
            model.addAttribute("libros", libros);
        } catch (UsuarioInexistente e) {
            return new ModelAndView("redirect:/login");
        } catch (ListaVacia e) {
            model.addAttribute("error", "No tiene libros con este estado");
        }

        model.addAttribute("categoriaActual", estadoDeLectura);
        return new ModelAndView("perfil", model);
    }
}
