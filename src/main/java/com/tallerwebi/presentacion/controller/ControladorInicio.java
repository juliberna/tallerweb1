package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.excepcion.ListaDeReviewsVacias;
import com.tallerwebi.dominio.excepcion.ListaVacia;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import com.tallerwebi.dominio.model.Resenia;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.model.UsuarioLibro;
import com.tallerwebi.infraestructura.service.ServicioInicio;
import com.tallerwebi.infraestructura.service.ServicioUsuario;
import com.tallerwebi.infraestructura.service.ServicioUsuarioLibro;
import com.tallerwebi.dominio.model.*;
import com.tallerwebi.infraestructura.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class ControladorInicio {

    private final ServicioInicio servicioInicio;
    private final ServicioUsuario servicioUsuario;
    private final ServicioUsuarioLibro servicioUsuarioLibro;
    private final ServicioNotificacion servicioNotificacion;
    private final ServicioPublicacion servicioPublicacion;
    private final ServicioUsuarioNotificacion servicioUsuarioNotificacion;

    private final ServicioComentarioPublicacion servicioComentarioPublicacion;

    @Autowired
    public ControladorInicio(ServicioInicio servicioInicio, ServicioUsuario servicioUsuario, ServicioUsuarioLibro servicioUsuarioLibro, ServicioUsuarioNotificacion servicioUsuarioNotificacion, ServicioNotificacion servicioNotificacion, ServicioPublicacion servicioPublicacion, ServicioPublicacion publicacion, ServicioComentarioPublicacion servicioComentarioPublicacion, ServicioComentarioPublicacion comentarioPublicacion) {
        this.servicioInicio = servicioInicio;
        this.servicioNotificacion = servicioNotificacion;
        this.servicioUsuario = servicioUsuario;
        this.servicioUsuarioLibro = servicioUsuarioLibro;
        this.servicioUsuarioNotificacion = servicioUsuarioNotificacion;
        this.servicioPublicacion = servicioPublicacion;
        this.servicioComentarioPublicacion = servicioComentarioPublicacion;
    }

    @GetMapping("/home")
    public String mostrarHome(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("USERID");
        Integer anioActual = (Integer) session.getAttribute("ANIOACTUAL");

        try {
            Usuario usuario = servicioUsuario.buscarUsuarioPorId(userId);
            model.addAttribute("usuario", usuario);

            List<UsuarioLibro> listadoComments = servicioUsuarioLibro.obtenerTodosLosComentariosDeMisAmigos(userId);

            model.addAttribute("comentariosDeAmigos", listadoComments);

            List<Publicacion> listadoAmigosPublicaciones = servicioPublicacion.obtenerTodasPublicacionesDeAmigos(userId);
            model.addAttribute("listadoAmigosPublicaciones", listadoAmigosPublicaciones);

            List<Long> publicacionIds = listadoAmigosPublicaciones.stream()
                    .map(Publicacion::getId)
                    .collect(Collectors.toList());

            Map<Long, List<ComentarioPublicacion>> comentariosPorPublicacion = new HashMap<>();

            for (Long publicacionId : publicacionIds) {
                List<ComentarioPublicacion> comentariosDeUnaPublicacion = servicioComentarioPublicacion.obtenerLosComentariosDeLaPublicacion(publicacionId);
                comentariosPorPublicacion.put(publicacionId, comentariosDeUnaPublicacion);
            }

            model.addAttribute("comentariosPorPublicacion", comentariosPorPublicacion);



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

            Integer porcentajeLibrosLeidos = 0;

            if(usuario.getMeta() != null) {
                porcentajeLibrosLeidos = (int) Math.round((double) cantidadLibrosLeidos / (double) usuario.getMeta() * 100);
            }

            model.addAttribute("porcentajeLibrosLeidos", porcentajeLibrosLeidos);
            List<Resenia> reviews = servicioInicio.cargarTodasLasReviews();
            model.addAttribute("reviews", reviews);
        } catch (UsuarioInexistente e) {
            return "redirect:/login";
        } catch (ListaDeReviewsVacias ex) {
            model.addAttribute("MensajeNoReviews", ex.getMessage());
        } catch (ListaVacia e) {
            model.addAttribute("cantidadLibrosLeidos", 0);
            model.addAttribute("porcentajeLibrosLeidos", 0);
        } catch (MessagingException e) {
            throw new RuntimeException(e.getMessage());
        }

        return "home";
    }


}


