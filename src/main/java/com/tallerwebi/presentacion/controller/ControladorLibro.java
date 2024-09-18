package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.infraestructura.service.ServicioLibro;
import com.tallerwebi.dominio.excepcion.ListaVacia;
import com.tallerwebi.dominio.excepcion.QueryVacia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
public class ControladorLibro {

    private ServicioLibro servicioLibro;

    @Autowired
    public ControladorLibro(ServicioLibro servicioLibro) {
        this.servicioLibro = servicioLibro;
    }

    @RequestMapping("/buscar")
    public ModelAndView buscarLibros(@RequestParam("query") String query) {
        ModelMap modelo = new ModelMap();

        Set<Libro> librosObtenidos = null;

        try {
            librosObtenidos = servicioLibro.buscar(query);
            modelo.addAttribute("libros",librosObtenidos);
        } catch (QueryVacia e) {
            modelo.addAttribute("error","El campo de busqueda esta vacio");
        } catch (ListaVacia e) {
            modelo.addAttribute("error","No se encontraron libros que coincidan con la busqueda");
        }

        modelo.addAttribute("query",query);
        return new ModelAndView("resultados_busqueda",modelo);
    }
}
