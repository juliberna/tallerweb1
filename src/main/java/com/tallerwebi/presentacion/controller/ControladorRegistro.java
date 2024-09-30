package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.infraestructura.service.ServicioLogin;
import com.tallerwebi.infraestructura.service.ServicioLoginImpl;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/usuario")
public class ControladorRegistro {

    private final ServicioLogin servicioLogin;

    @Autowired
    public ControladorRegistro(ServicioLogin servicioLogin) {
        this.servicioLogin = servicioLogin;
    }

    @GetMapping("/mostrarRegistro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "nuevo-usuario";
    }

    @RequestMapping(value = "/guardar", method = RequestMethod.POST)
    public String registrarUsuario(@RequestParam String email, @RequestParam String password, @RequestParam String nombreUsuario, @RequestParam String nombre, @RequestParam String fechaNacimiento) {
        try {
            System.out.println(email);
            System.out.println(password);
            System.out.println(nombreUsuario);
            System.out.println(nombre);
            System.out.println(fechaNacimiento);
            LocalDate fechaNac = LocalDate.parse(fechaNacimiento);
            servicioLogin.registrar(email, password, nombreUsuario, nombre, fechaNac);
            return "redirect:/login";
        } catch (UsuarioExistente e) {
            return "nuevo-usuario";
        }
    }

}
