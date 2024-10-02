package com.tallerwebi.presentacion.controller;

import com.tallerwebi.infraestructura.repository.RepositorioReview;
import com.tallerwebi.infraestructura.service.ServicioHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ControladorHome {

    private ServicioHome servicioHome;
    private RepositorioReview repositorioReview;

    @Autowired
    public ControladorHome(ServicioHome servicioHome) {

        this.servicioHome = servicioHome;
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView home(ModelMap model) { return new ModelAndView("home");}









}


