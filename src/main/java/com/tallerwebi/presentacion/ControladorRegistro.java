package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorRegistro {

    public ModelAndView registrar(String email, String contraseña, String segundaContraseña) {
        ModelMap modelo = new ModelMap();

        if(email.isEmpty()) {
            modelo.put("error", "el email es obligatorio. Por favor complete el campo para continuar");
        }
        if (contraseña.isEmpty() || segundaContraseña.isEmpty()) {
            modelo.put("error", "la contraseña es obligatoria. Por favor complete el campo para continuar");
        }

        if(!contraseña.equals(segundaContraseña)){
            modelo.put("error", "las contraseñas deben coincidir. Intente nuevamente");
        }

        if(!modelo.isEmpty()){ //si el modelo no esta vacio, osea que por lo menos se le paso como parametro un error, se muestra la pantalla de registro con los errores.
            return new ModelAndView("registro", modelo);
        }

        return new ModelAndView("redirect:/login");
    }


}

