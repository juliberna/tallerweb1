package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.excepcion.ReseniaInexistente;
import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.dominio.model.LikeDislike;
import com.tallerwebi.dominio.model.Resenia;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.repository.RepositorioLikeDislike;
import com.tallerwebi.dominio.repository.RepositorioResenia;
import com.tallerwebi.dominio.repository.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ServicioReseniaImpl implements ServicioResenia {

    private RepositorioResenia repositorioResenia;
    private RepositorioUsuario repositorioUsuario;
    private RepositorioLikeDislike repositorioLikeDislike;

    @Autowired
    public ServicioReseniaImpl(RepositorioResenia repositorioResenia, RepositorioUsuario repositorioUsuario, RepositorioLikeDislike repositorioLikeDislike) {
        this.repositorioResenia = repositorioResenia;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioLikeDislike = repositorioLikeDislike;
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
    public Resenia obtenerReseniaPorId(Long id) throws ReseniaInexistente {
        Resenia resenia = repositorioResenia.obtenerReseniaPorId(id);

        if (resenia == null) {
            throw new ReseniaInexistente("La resenia no existe.");
        }
        return resenia;
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

    @Override
    public void reaccionar(Long idUsuario, Long idResenia, boolean esLike) {
        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(idUsuario);
        Resenia resenia = repositorioResenia.obtenerReseniaPorId(idResenia);

        LikeDislike reaccionExistente = repositorioLikeDislike.obtenerReaccionDelUsuario(idResenia, idUsuario);

        if (reaccionExistente != null) {
            System.out.println("Actualiza el like o dislike");
            actualizarEliminarReaccion(usuario, resenia, reaccionExistente, esLike);
        } else {
            System.out.println("Crea una nueva reaccion");
            crearNuevaReaccion(resenia, usuario, esLike);
        }

        repositorioResenia.guardar(resenia);
    }

    @Override
    public Integer obtenerCantidadLikes(Long idResenia) {
        List<LikeDislike> likes = repositorioLikeDislike.obtenerLikesResenia(idResenia);

        if (likes.isEmpty()) {
            return 0;
        }

        return likes.size();
    }

    @Override
    public Integer obtenerCantidadDislikes(Long idResenia) {
        List<LikeDislike> dislikes = repositorioLikeDislike.obtenerDislikesResenia(idResenia);

        if (dislikes.isEmpty()) {
            return 0;
        }

        return dislikes.size();
    }

    @Override
    public LikeDislike obtenerReaccionUsuario(Long idResenia, Long userId) {
        return repositorioLikeDislike.obtenerReaccionDelUsuario(idResenia, userId);
    }

    private void actualizarEliminarReaccion(Usuario usuario, Resenia resenia, LikeDislike reaccionExistente, boolean esLike) {

        if (reaccionExistente.getEsLike() == esLike) {
            System.out.println("Elimina la reaccion de la bdd");
            repositorioLikeDislike.eliminar(reaccionExistente); // Elimina la reaccion si es igual a la existente
        } else {
            System.out.println("Cambia el like o dislike en la bdd");
            reaccionExistente.setEsLike(esLike); // Cambia de like a dislike, o viceversa
            repositorioLikeDislike.guardar(reaccionExistente);
        }

    }

    private void crearNuevaReaccion(Resenia resenia, Usuario usuario, boolean esLike) {
        LikeDislike nuevaReaccion = new LikeDislike();
        nuevaReaccion.setUsuario(usuario);
        nuevaReaccion.setResenia(resenia);
        nuevaReaccion.setEsLike(esLike);
        repositorioLikeDislike.guardar(nuevaReaccion);

    }


}
