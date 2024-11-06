package com.tallerwebi.infraestructura.repository;


import com.tallerwebi.dominio.model.*;
import com.tallerwebi.dominio.repository.RepositorioResenia;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioReseniaImpl implements RepositorioResenia {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioReseniaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Resenia obtenerReseniaPorId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria resenia = session.createCriteria(Resenia.class);
        resenia.add(Restrictions.eq("id", id));
        return (Resenia) resenia.uniqueResult();
    }

    @Override
    public void guardar(Resenia resenia) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(resenia);
    }

    @Override
    public List<Resenia> obtenerReseniasDeOtrosUsuarios(Long userId, Long idLibro) {
        Session session = sessionFactory.getCurrentSession();

        return session.createCriteria(Resenia.class)
                .createAlias("usuario", "u")
                .add(Restrictions.eq("libro.id", idLibro)) // Traer reseñas de ese libro especifico
                .add(Restrictions.ne("u.id", userId))  // Excluir reseñas del usuario actual
                .setMaxResults(4)  // Limitar a 4 resultados
                .list();
    }

    @Override
    public List<Resenia> obtenerReseniasDelLibro(Long idLibro) {
        Session session = sessionFactory.getCurrentSession();

        return session.createCriteria(Resenia.class)
                .createAlias("libro", "l")
                .add(Restrictions.eq("l.id", idLibro))
                .list();
    }

    @Override
    public Resenia obtenerReseniaDelUsuario(Long userId, Long idLibro) {
        Session session = sessionFactory.getCurrentSession();

        return (Resenia) session.createCriteria(Resenia.class)
                .createAlias("usuario", "u")
                .createAlias("libro", "l")
                .add(Restrictions.eq("u.id", userId))
                .add(Restrictions.eq("l.id", idLibro))
                .uniqueResult();
    }

    @Override
    public List<Resenia> obtenerTodasLasReseniasDelUsuario(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();

        return session.createCriteria(Resenia.class)
                .add(Restrictions.eq("usuario", usuario))
                .list();
    }
}
