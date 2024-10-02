package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.model.Genero;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.infraestructura.service.ServicioLogin;
import com.tallerwebi.infraestructura.service.ServicioOnboarding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/onboarding")
public class ControladorOnboarding {

    private final ServicioOnboarding servicioOnboarding;

    @Autowired
    public ControladorOnboarding(ServicioOnboarding servicioOnboarding) {
        this.servicioOnboarding = servicioOnboarding;
    }

    @RequestMapping(value = "/mostrarOnboarding/{id}", method = RequestMethod.GET)
    public ModelAndView mostrarFormularioOnboarding(@PathVariable Long id) {
        ModelMap model = new ModelMap();
        model.put("userId", id);
        List<Genero> generos = servicioOnboarding.obtenerGeneros();
        model.put("generos", generos);
        System.out.println(generos);
        return new ModelAndView("onboarding", model);
    }

    @RequestMapping(value = "/mostrarRegistroExitoso", method = RequestMethod.GET)
    public ModelAndView mostrarRegistroExitoso() {
        ModelMap model = new ModelMap();
        return new ModelAndView("registro-exitoso", model);
    }

    @RequestMapping(value = "/guardarGeneros", method = RequestMethod.POST)
    public String guardarGeneros(@RequestParam List<Long> generos) {
        System.out.println(generos + "generos");
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("USERID");
        if (userId != null) {
            servicioOnboarding.guardarGeneros(userId, generos);
            return "redirect:/onboarding/mostrarRegistroExitoso";
        } else {
            return "redirect:/login";
        }
    }

}
