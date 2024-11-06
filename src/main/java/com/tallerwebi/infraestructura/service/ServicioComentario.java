package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.excepcion.ListaVacia;
import com.tallerwebi.dominio.model.Comentario;
import com.tallerwebi.dominio.model.Usuario;

import java.util.List;

public interface ServicioComentario {
    void guardarComentario(Long idResenia, Usuario usuario,String textoComentario);
    List<Comentario> obtenerComentariosPorResenia(Long idResenia) throws ListaVacia;
}
