package com.tallerwebi.infraestructura.repository;

import com.tallerwebi.dominio.model.UsuarioGenero;
import com.tallerwebi.dominio.model.UsuarioLibro;
import com.tallerwebi.dominio.repository.RepositorioUsuarioGenero;
import com.tallerwebi.dominio.repository.RepositorioUsuarioLibro;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository

public class RepositorioUsuarioGeneroImpl implements RepositorioUsuarioGenero {

    public final SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioGeneroImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public UsuarioGenero encontrarUsuarioIdYGeneroId(Long usuarioId, Long generoId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(UsuarioGenero.class);


        criteria.add(Restrictions.eq("usuario.id", usuarioId));
        criteria.add(Restrictions.eq("genero.id", generoId));

        return (UsuarioGenero) criteria.uniqueResult();
    }

    @Override
    public void guardar(UsuarioGenero usuarioGenero) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(usuarioGenero);
    }
}
