package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.dominio.model.Resenia;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.repository.RepositorioResenia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioReseniaImpl implements ServicioResenia {

    private RepositorioResenia repositorioResenia;

    @Autowired
    public ServicioReseniaImpl(RepositorioResenia repositorioResenia) {
        this.repositorioResenia = repositorioResenia;
    }

    @Override
    public void guardarResenia(Usuario usuario, Libro libro, Integer puntuacion, String descripcion) {
        Resenia resenia = new Resenia();
        resenia.setUsuario(usuario);
        resenia.setLibro(libro);
        resenia.setPuntuacion(puntuacion);
        resenia.setDescripcion(descripcion);

        repositorioResenia.guardar(resenia);
    }

    @Override
    public List<Resenia> obtenerReseniasDeOtrosUsuarios(Long userId) {
        List<Resenia> resenias = repositorioResenia.obtenerReseniasDeOtrosUsuarios(userId);
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
