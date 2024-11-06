package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.excepcion.ListaVacia;
import com.tallerwebi.dominio.model.Comentario;
import com.tallerwebi.dominio.model.Resenia;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.repository.RepositorioComentario;
import com.tallerwebi.dominio.repository.RepositorioResenia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ServicioComentarioImpl implements ServicioComentario {

    private RepositorioComentario repositorioComentario;
    private RepositorioResenia repositorioResenia;

    @Autowired
    public ServicioComentarioImpl(RepositorioComentario repositorioComentario, RepositorioResenia repositorioResenia) {
        this.repositorioComentario = repositorioComentario;
        this.repositorioResenia = repositorioResenia;
    }

    @Override
    public void guardarComentario(Long idResenia, Usuario usuario, String textoComentario) {
        Resenia resenia = repositorioResenia.obtenerReseniaPorId(idResenia);
        if (resenia != null) {
            Comentario comentario = new Comentario();
            comentario.setResenia(resenia);
            comentario.setUsuario(usuario);
            comentario.setTexto(textoComentario);
            comentario.setFechaComentario(LocalDate.now());

            repositorioComentario.guardarComentario(comentario);
        }
    }

    @Override
    public List<Comentario> obtenerComentariosPorResenia(Long idResenia) throws ListaVacia {
        List<Comentario> comentarios = repositorioComentario.obtenerComentariosPorResenia(idResenia);

        if (comentarios.isEmpty()) {
            throw new ListaVacia("La reseña aún no tiene comentarios.");
        }
        return comentarios;
    }
}
