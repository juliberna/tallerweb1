package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.model.Usuario;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioInicioImpl implements ServicioInicio {


    @Override
    public void buscar(String nombre) {

    }

    @Override
    public void cargarReviews() {

    }

    @Override
    public void recomendarLibro(Usuario usuario) {



    }
}
