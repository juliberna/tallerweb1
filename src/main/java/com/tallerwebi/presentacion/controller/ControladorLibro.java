package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.excepcion.LibroNoEncontrado;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.model.UsuarioLibro;
import com.tallerwebi.infraestructura.service.ServicioLibro;
import com.tallerwebi.dominio.excepcion.ListaVacia;
import com.tallerwebi.dominio.excepcion.QueryVacia;
import com.tallerwebi.infraestructura.service.ServicioUsuario;
import com.tallerwebi.infraestructura.service.ServicioUsuarioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/libro")
public class ControladorLibro {

    private ServicioLibro servicioLibro;
    private ServicioUsuario servicioUsuario;
    private ServicioUsuarioLibro servicioUsuarioLibro;

    @Autowired
    public ControladorLibro(ServicioLibro servicioLibro, ServicioUsuario servicioUsuario, ServicioUsuarioLibro servicioUsuarioLibro) {
        this.servicioLibro = servicioLibro;
        this.servicioUsuario = servicioUsuario;
        this.servicioUsuarioLibro = servicioUsuarioLibro;
    }

    @RequestMapping("/buscar")
    public ModelAndView buscarLibros(@RequestParam("query") String query) {
        ModelMap modelo = new ModelMap();

        Set<Libro> librosObtenidos = null;

        try {
            librosObtenidos = servicioLibro.buscar(query);
            modelo.addAttribute("libros", librosObtenidos);
        } catch (QueryVacia e) {
            modelo.addAttribute("error", "El campo de busqueda esta vacio");
        } catch (ListaVacia e) {
            modelo.addAttribute("error", "No se encontraron libros que coincidan con la busqueda");
        }

        modelo.addAttribute("query", query);
        return new ModelAndView("resultados_busqueda", modelo);
    }

    @RequestMapping(value = "/detalle/{id}", method = RequestMethod.GET)
    public String detalleLibro(ModelMap model, @PathVariable Long id) {
        try {
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attr.getRequest();
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("USERID");

            Libro libro = servicioLibro.obtenerIdLibro(id);
            UsuarioLibro usuarioLibro = servicioUsuarioLibro.obtenerUsuarioLibro(userId, id);
            Double promedioDePuntuacion = servicioUsuarioLibro.calcularPromedioDePuntuacion(id);

            model.addAttribute("libro", libro);
            model.addAttribute("usuarioLibro", usuarioLibro);
            model.addAttribute("promedioDePuntuacion", promedioDePuntuacion);
            return "infoLibro";
        } catch (LibroNoEncontrado e) {
            model.addAttribute("error", e.getMessage());
            return "errorLibroNoEncontrado";  // Redirige a una vista de error si el libro no se encuentra
        }
    }

    @RequestMapping(value = "/cambiarEstadoDeLectura", method = RequestMethod.POST)
    public String cambiarEstadoDeLectura(ModelMap model, @RequestParam Long id, @RequestParam String status, RedirectAttributes redirectAttributes) {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("USERID");
        try {
            // Actualizar o crear la relación entre usuario y libro con el nuevo estado de lectura
            servicioUsuarioLibro.crearOActualizarUsuarioLibro(userId, id, status, null, null);

            if (status.equals("Leído")) {
                return "redirect:/libro/resena/" + id + "?usuarioId=" + userId;
            }

            redirectAttributes.addFlashAttribute("mensaje", "Tu estado de lectura es: " + status);
            return "redirect:/libro/detalle/" + id + "?usuarioId=" + userId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/libro/detalle/" + id + "?usuarioId=" + userId;
        }
    }

    @RequestMapping(value = "/resena/{id}", method = RequestMethod.GET)
    public String mostrarResenia(ModelMap model, @PathVariable Long id) {
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
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attr.getRequest();
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("USERID");
            servicioUsuarioLibro.crearOActualizarUsuarioLibro(userId, id, "Leído", puntuacion, reseña);
            return "redirect:/libro/detalle/" + id;
        } catch (LibroNoEncontrado e) {
            model.addAttribute("error", e.getMessage());
            return "errorLibroNoEncontrado";  // Redirige a una vista de error si el libro no se encuentra
        }
    }

    @RequestMapping(value = "/misLibros", method = RequestMethod.GET)
    public ModelAndView mostrarLibros(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("USERID");
        Integer anioActual = (Integer) session.getAttribute("ANIOACTUAL");

        try {
            Usuario usuario = servicioUsuario.buscarUsuarioPorId(userId);
            model.addAttribute("usuario", usuario);
            List<UsuarioLibro> libros = servicioUsuarioLibro.buscarPorEstadoDeLectura("Quiero Leer",usuario);
            model.addAttribute("libros", libros);
        } catch (UsuarioInexistente e) {
            return new ModelAndView("redirect:/login");
        } catch (ListaVacia e) {
            model.addAttribute("error", "No tiene libros con este estado");
        }
        return new ModelAndView("mostrar-libros", model);
    }

    @RequestMapping(value = "/misLibros/estanteria")
    public ModelAndView cambiarCategoria(HttpServletRequest request, @RequestParam("estado") String estadoDeLectura) {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Long idUsuario = (Long) session.getAttribute("USERID");

        try {
            Usuario usuario = servicioUsuario.buscarUsuarioPorId(idUsuario);
            model.addAttribute("usuario", usuario);
            List<UsuarioLibro> libros;

            if (estadoDeLectura.equals("Leído")) {
                libros = servicioUsuarioLibro.buscarLibrosLeidosPorAño(LocalDate.now().getYear(), usuario);
                Integer cantidadLibrosLeidos = libros.size();
                model.addAttribute("cantidadLibrosLeidos", cantidadLibrosLeidos);
            } else {
                libros = servicioUsuarioLibro.buscarPorEstadoDeLectura(estadoDeLectura, usuario);
            }

            model.addAttribute("libros", libros);

        } catch (UsuarioInexistente e) {
            return new ModelAndView("redirect:/login");
        } catch (ListaVacia e) {
            model.addAttribute("error", "No tiene libros con este estado");
            model.addAttribute("cantidadLibrosLeidos", 0);
        }

        model.addAttribute("categoriaActual", estadoDeLectura);
        return new ModelAndView("mostrar-libros", model);
    }

}
