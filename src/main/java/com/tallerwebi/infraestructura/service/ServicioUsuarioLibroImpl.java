package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.model.UsuarioLibro;
import com.tallerwebi.dominio.repository.RepositorioLibro;
import com.tallerwebi.dominio.repository.RepositorioUsuario;
import com.tallerwebi.dominio.repository.RepositorioUsuarioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioUsuarioLibroImpl implements ServicioUsuarioLibro {

    private final RepositorioUsuarioLibro repositorioUsuarioLibro;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioLibro repositorioLibro;

    @Autowired
    public ServicioUsuarioLibroImpl(RepositorioUsuarioLibro repositorioUsuarioLibro, RepositorioUsuario repositorioUsuario, RepositorioLibro repositorioLibro) {
        this.repositorioUsuarioLibro = repositorioUsuarioLibro;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioLibro = repositorioLibro;
    }

    @Override
    public UsuarioLibro obtenerUsuarioLibro(Long usuarioId, Long libroId) {
        // Llamamos al repositorio y devolvemos directamente el objeto o null
        return repositorioUsuarioLibro.encontrarUsuarioIdYLibroId(usuarioId, libroId);
    }

    @Override
    public void guardarUsuarioLibro(UsuarioLibro usuarioLibro) {
        System.out.println("Usuario " + usuarioLibro.getUsuario());
        System.out.println("Libro " + usuarioLibro.getLibro());
        repositorioUsuarioLibro.guardar(usuarioLibro);
    }

    @Override
    public void crearOActualizarUsuarioLibro(Long usuarioId, Long libroId, String estadoDeLectura, Integer puntuacion, String reseña) {
        UsuarioLibro usuarioLibro = repositorioUsuarioLibro.encontrarUsuarioIdYLibroId(usuarioId, libroId);

        if (usuarioLibro == null) {
            // Si no existe, crear una nueva relación
            usuarioLibro = new UsuarioLibro();

            // Obtener las entidades Usuario y Libro correspondientes
            Usuario usuario = repositorioUsuario.buscarUsuarioPorId(usuarioId);
            Libro libro = repositorioLibro.buscarLibroPorId(libroId);

            if (usuario == null || libro == null) {
                throw new IllegalArgumentException("Usuario o Libro no encontrado.");
            }

            // Asignar las entidades a UsuarioLibro
            usuarioLibro.setUsuario(usuario);
            usuarioLibro.setLibro(libro);
        }

        // Actualizar los atributos (estado de lectura, puntuación, reseña)
        usuarioLibro.setEstadoDeLectura(estadoDeLectura);
        usuarioLibro.setPuntuacion(puntuacion);
        usuarioLibro.setResena(reseña);

        // Guardar o actualizar la relación en la base de datos
        guardarUsuarioLibro(usuarioLibro);
    }


}
