package com.tallerwebi.infraestructura.repository;

import com.tallerwebi.dominio.model.*;
import com.tallerwebi.dominio.repository.RepositorioUsuario;
import com.tallerwebi.dominio.model.Usuario;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    private final RepositorioOnboardingImpl repositorioOnboarding;
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioImpl(SessionFactory sessionFactory, RepositorioOnboardingImpl repositorioOnboarding){
        this.sessionFactory = sessionFactory;
        this.repositorioOnboarding = repositorioOnboarding;
    }

    @Override
    public Usuario buscarUsuario(String email, String password) {

        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("password", password))
                .uniqueResult();
    }

    @Override
    public void guardar(String email, String password, String nombreUsuario, String nombre, LocalDate fechaNacimiento) {
        Session session = sessionFactory.getCurrentSession();
        Usuario usuario = new Usuario();

        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setNombre(nombre);
        usuario.setFechaNacimiento(fechaNacimiento);
        usuario.setRol("Usuario");
        usuario.setActivo(true);

        session.save(usuario);
    }

    @Override
    public Usuario buscar(String email) {
        return (Usuario) sessionFactory.getCurrentSession()
                .createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public void modificar(Usuario usuario) {
        sessionFactory.getCurrentSession().update(usuario);
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
            Session session = sessionFactory.getCurrentSession();
            Criteria usuario = session.createCriteria(Usuario.class);
            usuario.add(Restrictions.eq("id", id));
            Usuario usuarioEncontrado = (Usuario) usuario.uniqueResult();
            return usuarioEncontrado;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void guardarTokenDeRecuperacion(Usuario usuario, String token) {
        usuario.setTokenRecuperacion(token);
        entityManager.merge(usuario);
    }

//    @Override
//    public void guardarGeneros(Long usuarioId, List<Long> generos) {
//        Usuario usuario = buscarUsuarioPorId(usuarioId);
//
//        for (Long generoId : generos) {
//            Genero genero = repositorioOnboarding.obtenerGeneroPorId(generoId);
//            usuario.setGenero(genero);
//            guardarUsuario(usuario);
//        }
//    }

    @Override
    public void guardarUsuario(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(usuario);
    }

    @Override
    public void guardarUsuarioOnboarding(Usuario usuario) {
        sessionFactory.getCurrentSession().save(usuario);
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public Usuario buscarPorNombreUsuario(String nombreUsuario) {
        Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("nombreUsuario", nombreUsuario))
                .uniqueResult();
    }

}

