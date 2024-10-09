package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.excepcion.ListaVacia;
import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.model.UsuarioLibro;
import com.tallerwebi.dominio.repository.RepositorioLibro;
import com.tallerwebi.dominio.repository.RepositorioUsuario;
import com.tallerwebi.dominio.repository.RepositorioUsuarioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return repositorioUsuarioLibro.encontrarUsuarioIdYLibroId(usuarioId, libroId);
    }

    @Override
    public void guardarUsuarioLibro(UsuarioLibro usuarioLibro) {
        repositorioUsuarioLibro.guardar(usuarioLibro);
    }

    @Override
    public void crearOActualizarUsuarioLibro(Long usuarioId, Long libroId, String estadoDeLectura, Integer puntuacion, String resenia) {
        UsuarioLibro usuarioLibro = repositorioUsuarioLibro.encontrarUsuarioIdYLibroId(usuarioId, libroId);

        if (usuarioLibro == null) {
            // Si no existe, creo una nueva relación
            usuarioLibro = new UsuarioLibro();

            // Obtengo las entidades Usuario y Libro
            Usuario usuario = repositorioUsuario.buscarUsuarioPorId(usuarioId);
            Libro libro = repositorioLibro.buscarLibroPorId(libroId);

            if (usuario == null || libro == null) {
                throw new IllegalArgumentException("Usuario o Libro no encontrado.");
            }

            // Asigno las entidades a UsuarioLibro
            usuarioLibro.setUsuario(usuario);
            usuarioLibro.setLibro(libro);
        }

        // Actualizo los atributos
        usuarioLibro.setEstadoDeLectura(estadoDeLectura);
        usuarioLibro.setPuntuacion(puntuacion);
        usuarioLibro.setResenia(resenia);

        // Guardo o actualizo la relación en la base de datos
        guardarUsuarioLibro(usuarioLibro);
    }

    @Override
    public List<UsuarioLibro> buscarPorEstadoDeLectura(String estadoDeLectura, Usuario usuario) throws ListaVacia {
        List<UsuarioLibro> librosObtenidos = repositorioUsuarioLibro.buscarPorEstadoDeLectura(estadoDeLectura, usuario);

        if(librosObtenidos.isEmpty())
            throw new ListaVacia();

        return librosObtenidos;
    }

    @Override
    public Double calcularPromedioDePuntuacion(Long libroId) {
        List<UsuarioLibro> usuariosLibro = repositorioUsuarioLibro.buscarLibroPorId(libroId);
        if(usuariosLibro.isEmpty()){
            return 0.0;
        }

        Integer cantidad = 0;
        Integer suma = 0;

        for(UsuarioLibro usuarioLibro : usuariosLibro){
            if(usuarioLibro.getPuntuacion() != null){
                suma += usuarioLibro.getPuntuacion();
                cantidad++;
            }

        }

        if(cantidad == 0){
            return 0.0;
        }

        return (double)suma/cantidad;
    }


}
