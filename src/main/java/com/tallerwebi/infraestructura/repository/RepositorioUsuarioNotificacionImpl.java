package com.tallerwebi.infraestructura.repository;


import com.tallerwebi.dominio.model.UsuarioNotificacion;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioUsuarioNotificacionImpl implements RepositorioUsuarioNotificacion {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioNotificacionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<UsuarioNotificacion> listarNotificacionesPorUsuario(Long usuarioId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(UsuarioNotificacion.class);
        criteria.add(Restrictions.eq("usuario.id", usuarioId));
        return criteria.list();
    }

    @Override
    public void guardar(UsuarioNotificacion usuarioNotificacion) {
        System.out.println(usuarioNotificacion.getNotificacion() + " GET NOTIFICACION PAPAAAAA");
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(usuarioNotificacion);
    }
}