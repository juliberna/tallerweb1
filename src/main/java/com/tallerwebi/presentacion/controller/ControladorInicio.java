package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.excepcion.ListaDeReviewsVacias;
import com.tallerwebi.dominio.excepcion.ListaVacia;
import com.tallerwebi.dominio.model.Review;
import com.tallerwebi.dominio.repository.RepositorioReview;
import com.tallerwebi.infraestructura.service.ServicioInicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class ControladorInicio {

    private final ServicioInicio servicioInicio;

    @Autowired
    public ControladorInicio(ServicioInicio servicioInicio) {
        this.servicioInicio = servicioInicio;
    }

    @GetMapping("/home")
    public String mostrarHome(Model model) {
        try {
            List<Review> reviews = servicioInicio.cargarTodasLasReviews();
            model.addAttribute("reviews", reviews);
        } catch (ListaDeReviewsVacias ex) {
            model.addAttribute("MensajeNoReviews", ex.getMessage());
        }

        return "home";
    }









}


