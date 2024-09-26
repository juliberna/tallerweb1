package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.repository.RepositorioUsuario;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario){
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario consultarUsuario(String email, String password) {
        Usuario usuario = repositorioUsuario.buscarPorEmail(email);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }
        return null;
    }

    @Override
    public void registrar(Usuario usuario) throws UsuarioExistente {
        if (repositorioUsuario.buscarPorEmail(usuario.getEmail()) != null) {
            throw new UsuarioExistente();
        }
        repositorioUsuario.guardar(usuario);
    }

}



