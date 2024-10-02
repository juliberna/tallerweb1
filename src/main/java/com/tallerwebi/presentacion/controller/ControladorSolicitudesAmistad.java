package com.tallerwebi.presentacion.controller;

import com.tallerwebi.infraestructura.service.ServicioLibro;
import com.tallerwebi.infraestructura.service.ServicioSolicitudesAmistad;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/agregar-amigo")

public class ControladorSolicitudesAmistad {
    private ServicioSolicitudesAmistad servicioSolicitudesAmistad;

    @Autowired
    public ControladorSolicitudesAmistad(ServicioSolicitudesAmistad servicioSolicitudesAmistad) {
        this.servicioSolicitudesAmistad = servicioSolicitudesAmistad;
    }

    @RequestMapping(value = "/{friendId}", method = RequestMethod.POST)
    public String agregarAmigo(@PathVariable Long friendId) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("USERID");
        try {
            System.out.println(userId + " userId");
            System.out.println(friendId + " friendId");

            servicioSolicitudesAmistad.agregarAmigo(userId, friendId);
            servicioSolicitudesAmistad.agregarAmigo(friendId, userId);

            // aca podriamos tirar un modal
            // por el momento, se agrega de una, en un futuro
            // vamos a tirarle notificaciones al usuario para que ACEPTE la solicitud
            return "Amigo agregado correctamente";
        } catch (Exception e) {
            System.out.println(e.getMessage() + " GET MSGS");
            return e.getMessage();
        }
    }
}
