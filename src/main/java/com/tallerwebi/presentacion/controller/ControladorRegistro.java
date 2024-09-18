package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorRegistro {

    @GetMapping("/registro")
    public ModelAndView mostrarFormulario() {
        ModelAndView mav = new ModelAndView("nuevo-usuario");
        mav.addObject("usuario", new Usuario());
        mav.addObject("currentStep", 1); // Indicar que estamos en el paso 1
        return mav;
    }

    @PostMapping("/registrar")
    public ModelAndView registrarUsuario(@ModelAttribute Usuario usuario,
                                         @RequestParam("passwordConfirm") String passwordConfirm,
                                         @RequestParam("currentStep") int currentStep) {
        ModelMap modelo = new ModelMap();

        // Validar la información del primer paso
        if (currentStep == 1) {
            if (usuario.getEmail().isEmpty()) {
                modelo.put("errorMessage", "El email es obligatorio. Por favor complete el campo para continuar");
                return new ModelAndView("nuevo-usuario", modelo);
            }

            if (usuario.getPassword().isEmpty() || passwordConfirm.isEmpty()) {
                modelo.put("errorMessage", "La contraseña es obligatoria. Por favor complete el campo para continuar");
                return new ModelAndView("nuevo-usuario", modelo);
            }

            if (!usuario.getPassword().equals(passwordConfirm)) {
                modelo.put("errorMessage", "Las contraseñas deben coincidir. Intente nuevamente");
                return new ModelAndView("nuevo-usuario", modelo);
            }

            // Si todo está bien, pasar al siguiente paso
            modelo.put("usuario", usuario);
            modelo.put("currentStep", 2); // Ahora estamos en el paso 2
            return new ModelAndView("nuevo-usuario", modelo);
        }

        // Validar la información del segundo paso
        if (currentStep == 2) {
            if (usuario.getNombreUsuario().isEmpty()) {
                modelo.put("errorMessage", "El nombre de usuario es obligatorio");
                modelo.put("currentStep", 2);
                return new ModelAndView("nuevo-usuario", modelo);
            }

            // Si todo está bien, pasar al siguiente paso
            modelo.put("usuario", usuario);
            modelo.put("currentStep", 3); // Ahora estamos en el paso 3
            return new ModelAndView("nuevo-usuario", modelo);
        }

        // Validar la información del tercer paso y finalizar
        if (currentStep == 3) {
            if (usuario.getNombre().isEmpty() || usuario.getEdad() == null) {
                modelo.put("errorMessage", "El nombre y la edad son obligatorios");
                modelo.put("currentStep", 3);
                return new ModelAndView("nuevo-usuario", modelo);
            }

            // Aquí puedes guardar el usuario en la base de datos

            // Redirigir al login después de completar todos los pasos
            return new ModelAndView("redirect:/login");
        }

        // Si llegamos aquí, algo salió mal
        return new ModelAndView("nuevo-usuario", modelo);
    }

}

