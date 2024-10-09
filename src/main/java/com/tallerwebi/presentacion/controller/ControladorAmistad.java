package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.excepcion.LibroNoEncontrado;
import com.tallerwebi.infraestructura.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/solicitud-amistad")

public class ControladorAmistad {
    private ServicioAmistad servicioAmistad;
    private ServicioNotificacion servicioNotificacion;

    @Autowired
    public ControladorAmistad(ServicioAmistad servicioAmistad, ServicioNotificacion servicioNotificacion) {
        this.servicioAmistad = servicioAmistad;
        this.servicioNotificacion = servicioNotificacion;
    }

    @RequestMapping(value = "/{friendId}", method = RequestMethod.POST)
    public String enviarSolicitudAmistad(@PathVariable Long friendId) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("USERID");
        String username = (String) session.getAttribute("USERNAME");

        try {
            System.out.println(userId + " userId que quiere enviar la solicitud");
            System.out.println(friendId + " friendId que tiene que aceptarla");

            Boolean sent = servicioAmistad.enviarSolicitudDeAmistad(userId, friendId);
            if (sent) {
                servicioNotificacion.crearNotificacion(friendId, 2L, "Has recibido una solicitud de Amistad de " + username);
            }
            return "amigoAgregadoCorrectamente";
        } catch (Exception e) {
            System.out.println(e.getMessage() + " GET MSGS");
            return e.getMessage();
        }
    }

    /* ESTO ES UNA VISTA DE EJEMPLO PARA ENVIAR UNA SOLICITUD DE AMISTAD. HASTA QUE ESTE EL PERFIL FUNCIONAL. */
    @RequestMapping(value = "/pantalla-ejemplo-amigo" , method = RequestMethod.GET)
    public String enviarSolicitud(ModelMap model) {
        try {
            return "enviarSolicitud";
        } catch (LibroNoEncontrado e) {
            model.addAttribute("error", e.getMessage());
            return "home";
        }
    }
}
