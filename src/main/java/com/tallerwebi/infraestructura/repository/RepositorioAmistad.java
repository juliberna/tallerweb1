package com.tallerwebi.infraestructura.repository;

import com.tallerwebi.dominio.model.Amistad;

import java.util.List;

public interface RepositorioAmistad {
    Amistad encontrarAmistadPorUsuarios(Long usuarioId, Long amigoId);
    List<Amistad> listarSolicitudesDeAmistad(Long usuarioId);
    Boolean guardar(Amistad amistad);
}
