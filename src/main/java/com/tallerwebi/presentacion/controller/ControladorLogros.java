package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.excepcion.ListaVacia;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import com.tallerwebi.dominio.model.Logro;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.model.UsuarioLogro;
import com.tallerwebi.infraestructura.service.ServicioUsuario;
import com.tallerwebi.infraestructura.service.ServicioUsuarioLogro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorLogros {

    private ServicioUsuarioLogro servicioUsuarioLogro;
    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorLogros(ServicioUsuarioLogro servicioUsuarioLogro, ServicioUsuario servicioUsuario) {
        this.servicioUsuarioLogro = servicioUsuarioLogro;
        this.servicioUsuario = servicioUsuario;
    }

    @RequestMapping("/logros/{id}")
    public ModelAndView verLogros(@PathVariable Long id) {
        ModelMap model = new ModelMap();

        try {
            Usuario usuario = servicioUsuario.buscarUsuarioPorId(id);
            model.addAttribute("usuario", usuario);
            servicioUsuarioLogro.verificarYAsignarLogros(usuario);
            List<Logro> logrosPorCompletar = servicioUsuarioLogro.obtenerLogrosPorCompletar(usuario);
            model.addAttribute("logrosPorCompletar", logrosPorCompletar);

            List<UsuarioLogro> logrosDelUsuario = servicioUsuarioLogro.obtenerLogrosPorUsuario(usuario);
            model.addAttribute("logros", logrosDelUsuario);

            if(logrosPorCompletar.isEmpty()) {
                model.addAttribute("completados","Felicitaciones!! Ha completado todos los logros.");
            }
        } catch (UsuarioInexistente e) {
            return new ModelAndView("redirect:/login");
        } catch (ListaVacia e) {
            model.addAttribute("error", e.getMessage());
            System.out.println("Lista vacia");
        }

        return new ModelAndView("logros", model);
    }
}
