package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.dominio.model.Resenia;
import com.tallerwebi.dominio.model.Usuario;

import java.util.List;


public interface RepositorioResenia {

    void guardar(Resenia resenia);

    Resenia obtenerReseniaPorId(Long id);

    List<Resenia> obtenerReseniasDeOtrosUsuarios(Long userId);

    List<Resenia> obtenerReseniasDelLibro(Long idLibro);

    Resenia obtenerReseniaDelUsuario(Long userId, Long idLibro);

    List<Resenia> obtenerTodasLasReseniasDelUsuario(Usuario usuario);
}
