package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.RepositorioLibro;
import com.tallerwebi.dominio.ServicioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioLibroImpl implements ServicioLibro {

    RepositorioLibro repositorioLibro;

    @Autowired
    public ServicioLibroImpl(RepositorioLibro repositorioLibro) {
        this.repositorioLibro = repositorioLibro;
    }

    @Override
    public Libro obtenerIdLibro(Long id) {
        return repositorioLibro.buscarLibro(id);
    }

    @Override
    public void actualizarLibro(Libro libro) {
        repositorioLibro.actualizarLibro(libro);
    }


}
