package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.excepcion.ListaDeReviewsVacias;
import com.tallerwebi.dominio.excepcion.ListaVacia;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
<<<<<<< Updated upstream
import com.tallerwebi.dominio.model.Resenia;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.model.UsuarioLibro;
import com.tallerwebi.infraestructura.service.ServicioInicio;
import com.tallerwebi.infraestructura.service.ServicioUsuario;
import com.tallerwebi.infraestructura.service.ServicioUsuarioLibro;
=======
import com.tallerwebi.dominio.model.*;
import com.tallerwebi.infraestructura.service.*;
>>>>>>> Stashed changes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
<<<<<<< Updated upstream
=======
import java.util.ArrayList;
>>>>>>> Stashed changes
import java.util.List;


@Controller
public class ControladorInicio {

    private final ServicioInicio servicioInicio;
    private final ServicioUsuario servicioUsuario;
    private final ServicioUsuarioLibro servicioUsuarioLibro;
    private final ServicioNotificacion servicioNotificacion;
    private final ServicioUsuarioNotificacion servicioUsuarioNotificacion;

    @Autowired
    public ControladorInicio(ServicioInicio servicioInicio, ServicioUsuario servicioUsuario, ServicioUsuarioLibro servicioUsuarioLibro, ServicioUsuarioNotificacion servicioUsuarioNotificacion, ServicioNotificacion servicioNotificacion) {
        this.servicioInicio = servicioInicio;
        this.servicioNotificacion = servicioNotificacion;
        this.servicioUsuario = servicioUsuario;
        this.servicioUsuarioLibro = servicioUsuarioLibro;
        this.servicioUsuarioNotificacion = servicioUsuarioNotificacion;
    }

    @GetMapping("/home")
    public String mostrarHome(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("USERID");
        Integer anioActual = (Integer) session.getAttribute("ANIOACTUAL");

        try {
            Usuario usuario = servicioUsuario.buscarUsuarioPorId(userId);
            model.addAttribute("usuario", usuario);

            List<UsuarioNotificacion> notificacionesUsuario = servicioUsuarioNotificacion.listarNotificacionesPorUsuario(userId);

            List<Notificacion> listaNotificaciones = new ArrayList<>();

            for (UsuarioNotificacion usuarioNotificacion : notificacionesUsuario) {
                Long notificacionId = usuarioNotificacion.getNotificacion().getId();
                Notificacion notificacion = servicioNotificacion.obtenerNotificacionPorId(notificacionId);
                listaNotificaciones.add(notificacion);
            }

            model.addAttribute("notificaciones", listaNotificaciones);

            List<UsuarioLibro> librosLeidos = servicioUsuarioLibro.buscarLibrosLeidosPorAño(anioActual, usuario);
            Integer cantidadLibrosLeidos = librosLeidos.size();
            model.addAttribute("cantidadLibrosLeidos", cantidadLibrosLeidos);
            int porcentajeLibrosLeidos = (int) Math.round((double) cantidadLibrosLeidos / (double) usuario.getMeta() * 100);
            model.addAttribute("porcentajeLibrosLeidos", porcentajeLibrosLeidos);
<<<<<<< Updated upstream

=======
            List<Review> reviews = servicioInicio.cargarTodasLasReviews();
            model.addAttribute("reviews", reviews);
>>>>>>> Stashed changes
        } catch (UsuarioInexistente e) {
            return "redirect:/login";
        } catch (ListaDeReviewsVacias ex) {
            model.addAttribute("MensajeNoReviews", ex.getMessage());
        } catch (ListaVacia e) {
            model.addAttribute("cantidadLibrosLeidos", 0);
            model.addAttribute("porcentajeLibrosLeidos", 0);
        }

        return "home";
    }


}


