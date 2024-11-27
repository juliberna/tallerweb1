package com.tallerwebi.presentacion.controller;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.infraestructura.service.ServicioMercadoPago;
import com.tallerwebi.infraestructura.service.ServicioPlan;
import com.tallerwebi.infraestructura.service.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/planes")
public class ControladorPlanes {

    private ServicioPlan servicioPlan;
    private ServicioUsuario servicioUsuario;
    private ServicioMercadoPago servicioMercadoPago;

    @Autowired
    public ControladorPlanes(ServicioPlan servicioPlan, ServicioUsuario servicioUsuario, ServicioMercadoPago servicioMercadoPago) {
        this.servicioPlan = servicioPlan;
        this.servicioUsuario = servicioUsuario;
        this.servicioMercadoPago = servicioMercadoPago;
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

    @RequestMapping(value = "/actualizarPlan/{planId}", method = RequestMethod.POST)
    public String actualizarPlan(HttpServletRequest request, @PathVariable Long planId, ModelMap model, RedirectAttributes redirectAttributes) {
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

    // Se debe pasar el id del plan
    @PostMapping("/pagar/{planId}")
    public String pagarPlan(@PathVariable Long planId, Model model) {
        try {
            String linkDePago = servicioMercadoPago.crearPreferencia(planId);
            model.addAttribute("linkDePago", linkDePago);
            System.out.println("Link de pago: " + linkDePago);
            return "redireccionarPago"; // Vista para confirmar y redirigir
        } catch (MPException | MPApiException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/confirmarActualizar/{planId}")
    public String confirmarActualizarPlan(@PathVariable Long planId, Model model) {
        model.addAttribute("planId", planId);
        return "confirmarActualizarPlan";
    }
}
