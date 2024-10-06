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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.regex.Pattern;

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
    public String registrarUsuario(@RequestParam String email, @RequestParam String password, @RequestParam String nombreUsuario, @RequestParam String nombre, @RequestParam String fechaNacimiento, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!esContrasenaValida(password)) {
            redirectAttributes.addFlashAttribute("mensajeContrasena", "La contraseña debe contener al menos 8 caracteres, una letra mayúscula, una letra minúscula y un número.");
            redirectAttributes.addFlashAttribute("email", email);
            redirectAttributes.addFlashAttribute("nombreUsuario", nombreUsuario);
            redirectAttributes.addFlashAttribute("nombre", nombre);
            redirectAttributes.addFlashAttribute("fechaNacimiento", fechaNacimiento);
            return "redirect:/nuevo-usuario";
        }

        try {
            LocalDate fechaNac = LocalDate.parse(fechaNacimiento);
            servicioLogin.registrar(email, password, nombreUsuario, nombre, fechaNac);

            Usuario usuario = servicioLogin.buscar(email);
            session.setAttribute("USERID", usuario.getId());
            Long userId = (Long) session.getAttribute("USERID");
            return "redirect:/onboarding/mostrarOnboarding" + "/" + userId + "/1";
        } catch (UsuarioExistente e) {
            if (e.getMessage().contains("El email ya está en uso.")) {
                return "redirect:/recuperar-contrasena";

            }
            if (e.getMessage().contains("El nombre de usuario ya está en uso.")) {
                return "redirect:/recuperar-contrasena";
            }

            return "redirect:/nuevo-usuario";
        }
    }

    private boolean esContrasenaValida(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(password).matches();
    }
}
