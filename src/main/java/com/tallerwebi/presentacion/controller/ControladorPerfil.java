package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.excepcion.ListaVacia;
import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.infraestructura.service.ServicioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorPerfil {

    private ServicioLibro servicioLibro;

    @Autowired
    public ControladorPerfil(ServicioLibro servicioLibro) {
        this.servicioLibro = servicioLibro;
    }

    @RequestMapping(value = "/perfil",method = RequestMethod.GET)
    public ModelAndView mostrarPerfil() {
        ModelMap modelo = new ModelMap();

        List<Libro> librosLeyendo = null;
        try {
            // Por defecto el perfil muestra primero los libros leyendo
            librosLeyendo = servicioLibro.buscarPorEstadoDeLectura("Leyendo");
            modelo.addAttribute("libros", librosLeyendo);

            // TODO obtener usuario(Servicio usuario) y mandarlo en el modelo a la vista perfil
        } catch (ListaVacia e) {
            modelo.addAttribute("error", "No tiene libros con este estado");
        }

        return new ModelAndView("perfil", modelo);
    }

    @RequestMapping(value = "/perfil/estanteria")
    public ModelAndView cambiarCategoria(@RequestParam("estado")String estadoDeLectura) {
        ModelMap modelo = new ModelMap();

        // Obtener libros según la categoría seleccionada
        List<Libro> libros = null;
        try {
            libros = servicioLibro.buscarPorEstadoDeLectura(estadoDeLectura);
            modelo.addAttribute("libros", libros);
        } catch (ListaVacia e) {
            modelo.addAttribute("error", "No tiene libros con este estado");
        }

        modelo.addAttribute("categoriaActual", estadoDeLectura);
        return new ModelAndView("perfil",modelo);
    }
}
