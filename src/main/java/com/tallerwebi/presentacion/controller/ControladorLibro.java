package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.excepcion.LibroNoEncontrado;
import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.dominio.model.UsuarioLibro;
import com.tallerwebi.infraestructura.service.ServicioLibro;
import com.tallerwebi.dominio.excepcion.ListaVacia;
import com.tallerwebi.dominio.excepcion.QueryVacia;
import com.tallerwebi.infraestructura.service.ServicioUsuarioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Controller
@RequestMapping("/libro")
public class ControladorLibro {

    private ServicioLibro servicioLibro;
    private ServicioUsuarioLibro servicioUsuarioLibro;

    @Autowired
    public ControladorLibro(ServicioLibro servicioLibro, ServicioUsuarioLibro servicioUsuarioLibro) {
        this.servicioLibro = servicioLibro;
        this.servicioUsuarioLibro = servicioUsuarioLibro;
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

    @RequestMapping(value = "/detalle/{id}" , method = RequestMethod.GET)
    public String detalleLibro(ModelMap model, @PathVariable Long id) {
        try {
            Libro libro = servicioLibro.obtenerIdLibro(id);
            UsuarioLibro usuarioLibro = servicioUsuarioLibro.obtenerUsuarioLibro(2L, id);
            model.addAttribute("libro", libro);
            model.addAttribute("usuarioLibro", usuarioLibro);
            return "infoLibro";
        } catch (LibroNoEncontrado e) {
            model.addAttribute("error", e.getMessage());
            return "errorLibroNoEncontrado";  // Redirige a una vista de error si el libro no se encuentra
        }
    }

    @RequestMapping(value = "/cambiarEstadoDeLectura", method = RequestMethod.POST)
    public String cambiarEstadoDeLectura(ModelMap model, @RequestParam Long id, @RequestParam String status, RedirectAttributes redirectAttributes) {
        try {
            // Actualizar o crear la relación entre usuario y libro con el nuevo estado de lectura
            servicioUsuarioLibro.crearOActualizarUsuarioLibro(2L, id, status, null, null);

            if (status.equals("Leído")) {
                return "redirect:/libro/resena/" + id + "?usuarioId=" + 2L;
            }

            redirectAttributes.addFlashAttribute("mensaje", "Tu estado de lectura es: " + status);
            return "redirect:/libro/detalle/" + id + "?usuarioId=" + 2L;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/libro/detalle/" + id + "?usuarioId=" + 2L;
        }
    }

    @RequestMapping(value = "/resena/{id}" , method = RequestMethod.GET)
    public String mostrarResena(ModelMap model, @PathVariable Long id) {
        try {
            Libro libro = servicioLibro.obtenerIdLibro(id);
            model.addAttribute("libro", libro);
            return "resenaLibro";
        } catch (LibroNoEncontrado e) {
            model.addAttribute("error", e.getMessage());
            return "errorLibroNoEncontrado";  // Redirige a una vista de error si el libro no se encuentra
        }
    }

    @RequestMapping(value = "/guardarResena", method = RequestMethod.POST)
    public String guardarResena(ModelMap model, @RequestParam Long id, @RequestParam Integer puntuacion, @RequestParam String reseña) {
        try {
            servicioUsuarioLibro.crearOActualizarUsuarioLibro(2L, id, "Leído", puntuacion, reseña);
            return "redirect:/libro/detalle/" + id;
        } catch (LibroNoEncontrado e) {
            model.addAttribute("error", e.getMessage());
            return "errorLibroNoEncontrado";  // Redirige a una vista de error si el libro no se encuentra
        }
    }


}
