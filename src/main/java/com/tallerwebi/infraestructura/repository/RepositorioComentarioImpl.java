package com.tallerwebi.infraestructura.repository;

import com.tallerwebi.dominio.model.Comentario;
import com.tallerwebi.dominio.repository.RepositorioComentario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioComentarioImpl implements RepositorioComentario {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioComentarioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarComentario(Comentario comentario) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(comentario);
    }

    @Override
    public List<Comentario> obtenerComentariosPorResenia(Long idResenia) {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Comentario.class)
                .add(Restrictions.eq("resenia.id", idResenia))
                .list();
    }
}
