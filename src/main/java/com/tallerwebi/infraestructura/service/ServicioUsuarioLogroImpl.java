package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.excepcion.ListaVacia;
import com.tallerwebi.dominio.model.*;
import com.tallerwebi.dominio.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ServicioUsuarioLogroImpl implements ServicioUsuarioLogro {

    private RepositorioLogro repositorioLogro;
    private RepositorioUsuarioLogro repositorioUsuarioLogro;
    private RepositorioUsuarioLibro repositorioUsuarioLibro;
    private RepositorioLibroGenero repositorioLibroGenero;
    private RepositorioResenia repositorioResenia;

    @Autowired
    public ServicioUsuarioLogroImpl(RepositorioLogro repositorioLogro, RepositorioUsuarioLogro repositorioUsuarioLogro,
                                    RepositorioUsuarioLibro repositorioUsuarioLibro, RepositorioLibroGenero repositorioLibroGenero,
                                    RepositorioResenia repositorioResenia) {
        this.repositorioLogro = repositorioLogro;
        this.repositorioUsuarioLogro = repositorioUsuarioLogro;
        this.repositorioUsuarioLibro = repositorioUsuarioLibro;
        this.repositorioLibroGenero = repositorioLibroGenero;
        this.repositorioResenia = repositorioResenia;
    }


    @Override
    public void verificarYAsignarLogros(Usuario usuario) throws ListaVacia {
        List<Logro> logros = repositorioLogro.obtenerTodos();

        if (logros.isEmpty()) {
            throw new ListaVacia("No hay logros establecidos en la BD.");
        }

        for (Logro logro : logros) {
            boolean cumpleRequisito = verificarCumplimiento(logro, usuario);
            if (cumpleRequisito && !usuarioTieneLogro(usuario, logro)) {
                otorgarLogro(usuario, logro);
            }
        }
    }

    @Override
    public List<Logro> obtenerLogrosPorCompletar(Usuario usuario) {
        List<UsuarioLogro> logrosDelUsuario = repositorioUsuarioLogro.obtenerLogrosPorUsuario(usuario);
        List<Logro> logros = repositorioLogro.obtenerTodos();
        List<Logro> logrosPorCompletar = new ArrayList<>();

        Set<Logro> logrosAlcanzados = new HashSet<>();
        for (UsuarioLogro usuarioLogro : logrosDelUsuario) {
            logrosAlcanzados.add(usuarioLogro.getLogro());
        }

        for (Logro logro : logros) {
            if (!logrosAlcanzados.contains(logro)) {
                logrosPorCompletar.add(logro);
            }
        }

        return logrosPorCompletar;
    }

    @Override
    public List<UsuarioLogro> obtenerLogrosPorUsuario(Usuario usuario) throws ListaVacia {
        List<UsuarioLogro> logrosDelUsuario = repositorioUsuarioLogro.obtenerLogrosPorUsuario(usuario);

        if (logrosDelUsuario.isEmpty()) {
            throw new ListaVacia("El usuario aún no tiene logros.");
        }

        return logrosDelUsuario;
    }

    private boolean verificarCumplimiento(Logro logro, Usuario usuario) {
        List<UsuarioLibro> librosLeidos = repositorioUsuarioLibro.buscarLibrosLeidosPorAño(LocalDate.now().getYear(), usuario);
        Integer cantidadLibrosLeidos = librosLeidos.size();

        Integer cantidadGenerosLeidos = obtenerGenerosLeidos(librosLeidos);
        System.out.println("Cantidad de generos leidos: " + cantidadGenerosLeidos);

        List<Resenia> reseniasDadas = repositorioResenia.obtenerTodasLasReseniasDelUsuario(usuario);
        Integer cantidadReseniasDadas = reseniasDadas.size();
        System.out.println("Cantidad de reseñas dadas: " + cantidadReseniasDadas);

        switch (logro.getTipo()) {
            case LIBROS_LEIDOS:
                return cantidadLibrosLeidos >= logro.getObjetivo();
            case DESAFIO_COMPLETADO:
                if(usuario.getMeta() != null) {
                    return cantidadLibrosLeidos >= usuario.getMeta();
                } else {
                    return false;
                }
            case GENEROS_LEIDOS:
                return cantidadGenerosLeidos >= logro.getObjetivo();
            case RESENIAS_DADAS:
                return cantidadReseniasDadas >= logro.getObjetivo();
            default:
                return false;
        }
    }

    private Integer obtenerGenerosLeidos(List<UsuarioLibro> librosLeidos) {
        Set<Genero> generosLeidos = new HashSet<>();
        List<LibroGenero> libroGeneros = repositorioLibroGenero.obtenerLibroGeneros();

        for (LibroGenero libroGenero : libroGeneros) {
            for (UsuarioLibro usuarioLibro : librosLeidos) {
                if (libroGenero.getLibro().equals(usuarioLibro.getLibro())) {
                    generosLeidos.add(libroGenero.getGenero());
                }
            }
        }

        return generosLeidos.size();
    }

    private boolean usuarioTieneLogro(Usuario usuario, Logro logro) {
        UsuarioLogro usuarioLogro = repositorioUsuarioLogro.buscarUsuarioLogro(usuario, logro);
        return usuarioLogro != null;
    }

    private void otorgarLogro(Usuario usuario, Logro logro) {
        if (usuarioTieneLogro(usuario, logro)) {
            System.out.println("El usuario ya tiene ese logro");
            return;
        }
        UsuarioLogro usuarioLogro = new UsuarioLogro();
        usuarioLogro.setLogro(logro);
        usuarioLogro.setUsuario(usuario);
        repositorioUsuarioLogro.guardar(usuarioLogro);
    }
}
