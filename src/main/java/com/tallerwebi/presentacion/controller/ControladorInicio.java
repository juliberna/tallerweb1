package com.tallerwebi.presentacion.controller;

import com.tallerwebi.infraestructura.repository.RepositorioReview;
import com.tallerwebi.infraestructura.service.ServicioInicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ControladorInicio {

    private ServicioInicio servicioInicio;
    private RepositorioReview repositorioReview;

    @Autowired
    public ControladorInicio(ServicioInicio servicioInicio) {

        this.servicioInicio = servicioInicio;
    }
    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView home(ModelMap model) { return new ModelAndView("home");}









}


