package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.infraestructura.service.ServicioPlan;
import com.tallerwebi.infraestructura.service.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/planes")
public class ControladorPlanes {

    private ServicioPlan servicioPlan;
    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorPlanes(ServicioPlan servicioPlan, ServicioUsuario servicioUsuario) {
        this.servicioPlan = servicioPlan;
        this.servicioUsuario = servicioUsuario;
    }



    @RequestMapping("/mostrar")
    public String mostrar(ModelMap model, HttpServletRequest request) throws UsuarioInexistente {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("USERID");

        if (userId != null) {
            Usuario usuario = servicioUsuario.buscarUsuarioPorId(userId);
            session.setAttribute("planAdquirido", usuario.getPlan().getId());
            model.addAttribute("usuario", usuario);
            model.addAttribute("fechaPlanVenc", usuario.getFecha_plan_venc());
        }

        return "planes";
    }

    @RequestMapping(value = "/actualizarPlan/{planId}" , method = RequestMethod.POST)
    public String actualizarPlan(HttpServletRequest request, @PathVariable Long planId, ModelMap model) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("USERID");

        try {
            Usuario usuario = servicioUsuario.buscarUsuarioPorId(userId);


            servicioPlan.actualizarPlanDelUsuario(planId, userId);
            session.setAttribute("planAdquirido", planId);

            model.addAttribute("usuario", usuario);


            if (usuario.getPlan() != null) {
                model.addAttribute("plan", usuario.getPlan().getNombre());
            }

            return "redirect:/planes/mostrar";

        } catch (UsuarioInexistente e) {
            return "redirect:/login";
        }

        //return "planes";
    }
}
