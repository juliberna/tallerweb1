package com.tallerwebi.infraestructura.repository;

import com.tallerwebi.dominio.model.Logro;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.model.UsuarioLogro;
import com.tallerwebi.dominio.repository.RepositorioUsuarioLogro;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioUsuarioLogroImpl implements RepositorioUsuarioLogro {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioLogroImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(UsuarioLogro usuario) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(usuario);
    }

    @Override
    public UsuarioLogro buscarUsuarioLogro(Usuario usuario, Logro logro) {
        Session session = sessionFactory.getCurrentSession();

        return (UsuarioLogro) session.createCriteria(UsuarioLogro.class)
                .add(Restrictions.eq("usuario", usuario))
                .add(Restrictions.eq("logro", logro))
                .uniqueResult();
    }

    @Override
    public List<UsuarioLogro> obtenerLogrosPorUsuario(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(UsuarioLogro.class)
                .add(Restrictions.eq("usuario", usuario))
                .list();
    }

    @Override
    public List<UsuarioLogro> obtenerTodos() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(UsuarioLogro.class).list();
    }
}