package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.Autor;
import com.tallerwebi.dominio.model.Genero;
import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.dominio.model.Usuario;

import java.time.LocalDate;
import java.util.List;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);
    void guardar(String email, String password, String nombreUsuario, String nombre, LocalDate fechaNacimiento);
    void modificar(Usuario usuario);
    Usuario buscar(String email);
    Usuario buscarUsuarioPorId(Long id);
    void guardarUsuario(Usuario usuario);
    void guardarTokenDeRecuperacion(Usuario usuario, String token);
//    void guardarGeneros(Long usuarioId, List<Long> generos);
    void guardarUsuarioOnboarding(Usuario usuario);
    Usuario buscarPorEmail(String email);
    Usuario buscarPorNombreUsuario(String nombreUsuario);
    List<Usuario> obtenerUsuarios();
    List<Usuario> obtenerUsuariosDesafio(Long userId);
//    void guardarGeneros(Long usuarioId, List<Long> generos);
//    void guardarAutores(Long usuarioId, List<Long> autores);
//    void guardarLibros(Long usuarioId, List<Long> libros);
}

