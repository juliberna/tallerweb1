package com.tallerwebi.infraestructura.repository;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.repository.RepositorioUsuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    private SessionFactory sessionFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public RepositorioUsuarioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Usuario usuario = session.get(Usuario.class, email);
            if (usuario == null) {
                throw new UsuarioNoExistente("Usuario no encontrado con email: " + email);
            }
            return usuario;
        } catch (Exception e) {
            throw new UsuarioNoExistente("Error al buscar el usuario con email: " + email);
        }
    }

    @Override
    public Usuario buscarPorEmailPass(String email, String password) {
        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("password", password))
                .uniqueResult();
    }

    @Override
    public void guardar(Usuario usuario) {
        sessionFactory.getCurrentSession().save(usuario);
    }

    @Override
    public void modificar(Usuario usuario) {
        sessionFactory.getCurrentSession().saveOrUpdate(usuario);
    }

    @Override
    public void guardarTokenDeRecuperacion(Usuario usuario, String token) {
        usuario.setTokenRecuperacion(token);
        entityManager.merge(usuario);
    }
}

