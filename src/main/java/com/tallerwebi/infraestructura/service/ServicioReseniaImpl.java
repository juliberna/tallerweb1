package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.dominio.model.Resenia;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.model.UsuarioLibro;
import com.tallerwebi.dominio.repository.RepositorioResenia;
import com.tallerwebi.dominio.repository.RepositorioUsuarioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioReseniaImpl implements ServicioResenia {

    private RepositorioResenia repositorioResenia;
    private RepositorioUsuarioLibro repositorioUsuarioLibro;

    @Autowired
    public ServicioReseniaImpl(RepositorioResenia repositorioResenia, RepositorioUsuarioLibro repositorioUsuarioLibro) {
        this.repositorioResenia = repositorioResenia;
        this.repositorioUsuarioLibro = repositorioUsuarioLibro;
    }

    @Override
    public void guardarResenia(Usuario usuario, Libro libro, Integer puntuacion, String descripcion) {
        Resenia reseniaExistente = repositorioResenia.obtenerReseniaDelUsuario(usuario.getId(), libro.getId());
        UsuarioLibro usuarioLibro = repositorioUsuarioLibro.encontrarUsuarioIdYLibroId(usuario.getId(), libro.getId());

        if (reseniaExistente != null) {
            // Si ya existe una reseña, actualiza la descripcion y la puntuacion
            reseniaExistente.setDescripcion(descripcion);
            reseniaExistente.setPuntuacion(puntuacion);

            // Settear la resenia y la puntuacion en el UsuarioLibro
            usuarioLibro.setResenia(descripcion);
            usuarioLibro.setPuntuacion(puntuacion);

            repositorioResenia.guardar(reseniaExistente);
            repositorioUsuarioLibro.guardar(usuarioLibro);
        } else {
            // Si no existe una reseña, crea una nueva
            Resenia resenia = new Resenia();
            resenia.setUsuario(usuario);
            resenia.setLibro(libro);
            resenia.setPuntuacion(puntuacion);
            resenia.setDescripcion(descripcion);

            // Settear la resenia y la puntuacion en el UsuarioLibro
            usuarioLibro.setResenia(descripcion);
            usuarioLibro.setPuntuacion(puntuacion);

            repositorioResenia.guardar(resenia);
            repositorioUsuarioLibro.guardar(usuarioLibro);
        }
    }

    @Override
    public List<Resenia> obtenerReseniasDeOtrosUsuarios(Long userId,Long idLibro) {
        List<Resenia> resenias = repositorioResenia.obtenerReseniasDeOtrosUsuarios(userId,idLibro);
        return resenias;
    }

    @Override
    public Double calcularPromedioPuntuacion(Long idLibro) {
        List<Resenia> reseniasDelLibro = repositorioResenia.obtenerReseniasDelLibro(idLibro);

        if (reseniasDelLibro.isEmpty()) {
            return 0.0;
        }

        Integer cantidad = reseniasDelLibro.size();
        Integer suma = 0;

        for (Resenia resenia : reseniasDelLibro) {
            suma += resenia.getPuntuacion();
        }

        return (double) suma / cantidad;
    }

    @Override
    public Resenia obtenerReseniaDelUsuario(Long userId, Long idLibro) {
        Resenia resenia = repositorioResenia.obtenerReseniaDelUsuario(userId, idLibro);
        return resenia;
    }
}
