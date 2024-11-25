package com.tallerwebi.presentacion.controller;

import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/pago")
public class ControladorPago {

    @GetMapping("/exito")
    public String pagoExitoso(@RequestParam("payment_id") String paymentId,
                              @RequestParam("status") String status,
                              @RequestParam("external_reference") String externalReference,
                              HttpSession session) {
        // Recupera el ID del plan desde la referencia externa
        Long planId = Long.parseLong(externalReference);
        String mensajeEstadoPago = "Pago exitoso. ID de pago: " + paymentId + ", Estado: " + status;
        session.setAttribute("mensajeEstadoPago", mensajeEstadoPago);

        return "redirect:/planes/confirmarActualizar/" + planId;
    }

    @GetMapping("/error")
    public String pagoError(@RequestParam(value = "payment_id", required = false) String paymentId,
                            @RequestParam(value = "status", required = false) String status,
                            @RequestParam(value = "external_reference", required = false) String externalReference,
                            RedirectAttributes redirectAttributes) {
        // Procesa el error de pago
        String mensajeEstadoPago = "Error en el pago. ID de pago: " + paymentId + ", Estado: " + status;
        redirectAttributes.addFlashAttribute("mensajeEstadoPago", mensajeEstadoPago);
        return "redirect:/planes/mostrar";
    }

    @GetMapping("/pendiente")
    public String pagoPendiente(@RequestParam("payment_id") String paymentId,
                                @RequestParam("status") String status,
                                @RequestParam("external_reference") String externalReference,
                                RedirectAttributes redirectAttributes) {
        // Procesa el pago pendiente
        String mensajeEstadoPago = "Pago pendiente. ID de pago: " + paymentId + ", Estado: " + status;
        redirectAttributes.addFlashAttribute("mensajeEstadoPago", mensajeEstadoPago);
        return "redirect:/planes/mostrar";
    }
}
