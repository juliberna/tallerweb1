package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.excepcion.ListaVacia;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.model.UsuarioLibro;
import com.tallerwebi.infraestructura.service.ServicioLibro;
import com.tallerwebi.infraestructura.service.ServicioUsuario;
import com.tallerwebi.infraestructura.service.ServicioUsuarioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ControladorPerfil {

    private ServicioUsuario servicioUsuario;
    private ServicioUsuarioLibro servicioUsuarioLibro;

    @Autowired
    public ControladorPerfil(ServicioUsuarioLibro servicioUsuarioLibro, ServicioUsuario servicioUsuario) {
        this.servicioUsuarioLibro = servicioUsuarioLibro;
        this.servicioUsuario = servicioUsuario;
    }

    @RequestMapping(value = "/perfil/{id}", method = RequestMethod.GET)
    public ModelAndView mostrarPerfil(@PathVariable Long id) {
        ModelMap model = new ModelMap();

        try {
            Usuario usuario = servicioUsuario.buscarUsuarioPorId(id);
            model.addAttribute("usuario", usuario);
            List<UsuarioLibro> libros = servicioUsuarioLibro.buscarPorEstadoDeLectura("Leyendo", usuario);
            model.addAttribute("libros", libros);

        } catch (UsuarioInexistente e) {
            return new ModelAndView("redirect:/login");
        } catch (ListaVacia e) {
            model.addAttribute("error", "No tiene libros con este estado");
        }
        return new ModelAndView("perfil", model);
    }

    @RequestMapping(value = "/perfil/{id}/estanteria")
    public ModelAndView cambiarCategoria(@PathVariable Long id, @RequestParam("estado") String estadoDeLectura) {
        ModelMap model = new ModelMap();

        try {
            Usuario usuario = servicioUsuario.buscarUsuarioPorId(id);
            model.addAttribute("usuario", usuario);
            List<UsuarioLibro> libros = servicioUsuarioLibro.buscarPorEstadoDeLectura(estadoDeLectura, usuario);
            model.addAttribute("libros", libros);

            if (estadoDeLectura.equals("Leído")) {
                Integer cantidadLibrosLeidos = libros.size();
                model.addAttribute("cantidadLibrosLeidos", cantidadLibrosLeidos);
            }

        } catch (UsuarioInexistente e) {
            return new ModelAndView("redirect:/login");
        } catch (ListaVacia e) {
            model.addAttribute("error", "No tiene libros con este estado");
            model.addAttribute("cantidadLibrosLeidos", 0);
        }

        model.addAttribute("categoriaActual", estadoDeLectura);
        return new ModelAndView("perfil", model);
    }

    @RequestMapping(value = "/mostrarEditarPerfil/{id}")
    public ModelAndView mostrarEditarPerfil(@PathVariable Long id) {
        ModelMap model = new ModelMap();

        try {
            Usuario usuario = servicioUsuario.buscarUsuarioPorId(id);
            model.addAttribute("usuario", usuario);
            return new ModelAndView("editarPerfil", model);
        } catch (UsuarioInexistente e) {
            return new ModelAndView("redirect:/login");
        }
    }

    @RequestMapping(value = "/editarPerfil", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ModelAndView editarPerfil(@ModelAttribute Usuario usuario,
                                     @RequestParam("imagenPerfil") MultipartFile imagenPerfil,
                                     @RequestParam("imagenActual") String imagenActual,
                                     HttpServletRequest request) {
        ModelMap model = new ModelMap();

        // Obtener el id del usuario actual desde la sesión
        HttpSession session = request.getSession();
        Long idUsuarioActual = (Long) session.getAttribute("USERID");

        try {
            // Validar la imagen
            if (validarErroresImagenPerfil(usuario, imagenPerfil, imagenActual, idUsuarioActual, model)) {
                return new ModelAndView("editarPerfil", model);
            }

            // Validar nombre de usuario y correo electrónico
            if (validarErroresFormulario(usuario, idUsuarioActual, model)) {
                return new ModelAndView("editarPerfil", model);
            }

            // Actualizar el usuario en la base de datos
            servicioUsuario.actualizarUsuario(idUsuarioActual, usuario);

            // Redirigir al perfil
            return new ModelAndView("redirect:/perfil/" + idUsuarioActual, model);
        } catch (IOException e) {
            return new ModelAndView("editarPerfil", model);
        } catch (UsuarioInexistente e) {
            return new ModelAndView("redirect:/login");
        }
    }

    private boolean validarErroresImagenPerfil(Usuario usuario, MultipartFile imagenPerfil, String imagenActual, Long idUsuarioActual, ModelMap model) throws IOException {

        // Si no se sube una nueva imagen, mantener la imagen actual
        if (imagenPerfil.isEmpty()) {
            usuario.setImagenUrl(imagenActual);
            return false;
        }

        // (solo jpg o png)
        if (!imagenPerfil.isEmpty() && !esFormatoValido(imagenPerfil.getContentType())) {
            model.addAttribute("errorFormato", "El formato de la imagen debe ser JPG o PNG.");
            usuario.setImagenUrl(imagenActual);
            return true;
        }

        // Si el usuario tenía una imagen anterior, eliminarla del proyecto
        if (imagenActual != null && !imagenActual.isEmpty()) {
            eliminarImagen(imagenActual);
            guardarNuevaImagen(usuario, imagenPerfil, idUsuarioActual);
        }

        return false;
    }

    private boolean validarErroresFormulario(Usuario usuario, Long idUsuarioActual, ModelMap model) {
        if (servicioUsuario.existeNombreUsuario(usuario.getNombreUsuario(), idUsuarioActual)) {
            model.addAttribute("errorNombreUsuario", "El nombre de usuario ya existe. Por favor, elige otro.");
            model.addAttribute("usuario", usuario); // Mantener los datos del usuario en el formulario
            return true;
        }

        if (servicioUsuario.existeEmailUsuario(usuario.getEmail(), idUsuarioActual)) {
            model.addAttribute("errorEmail", "El email ya está registrado. Por favor, utiliza otro.");
            model.addAttribute("usuario", usuario); // Mantener los datos del usuario en el formulario
            return true;
        }

        return false;
    }

    private boolean esFormatoValido(String contentType) {
        return contentType.equals("image/jpeg") || contentType.equals("image/png");
    }

    private void guardarNuevaImagen(Usuario usuario, MultipartFile imagenPerfil, Long idUsuarioActual) throws IOException {
        String rutaImagenes = "src/main/webapp/resources/core/Images/";
        String nombreArchivo = idUsuarioActual + "-" + imagenPerfil.getOriginalFilename();
        File archivoDestino = new File(rutaImagenes + nombreArchivo);

        // Guardar el archivo en la ubicación especificada
        imagenPerfil.transferTo(archivoDestino);

        // Actualizar la URL de la imagen en el objeto usuario
        usuario.setImagenUrl("/Images/" + nombreArchivo);
    }

    private void eliminarImagen(String imagenUrl) {
        try {
            Path path = Paths.get("src/main/webapp/resources/core" + imagenUrl);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
