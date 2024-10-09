package com.tallerwebi.infraestructura.repository;
import com.tallerwebi.dominio.model.Notificacion;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioNotificacionImpl implements RepositorioNotificacion {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioNotificacionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Notificacion encontrarNotificacionPorId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Notificacion.class);
        criteria.add(Restrictions.eq("id", id));
        return (Notificacion) criteria.uniqueResult();
    }

    @Override
    public void guardar(Notificacion notificacion) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(notificacion);
    }
}