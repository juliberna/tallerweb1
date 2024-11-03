package com.tallerwebi.infraestructura.repository;
import com.tallerwebi.dominio.model.Amistad;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class RepositorioAmistadImpl implements RepositorioAmistad {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioAmistadImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Amistad encontrarAmistadPorUsuarios(Long usuarioId, Long amigoId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Amistad.class);
        criteria.add(Restrictions.eq("usuario.id", usuarioId));
        criteria.add(Restrictions.eq("amigo.id", amigoId));
        return (Amistad) criteria.uniqueResult();
    }

    @Override
    public List<Amistad> listarSolicitudesDeAmistad(Long usuarioId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Amistad.class);
        criteria.add(Restrictions.eq("amigo.id", usuarioId));
        criteria.add(Restrictions.eq("estado", "pendiente"));
        return criteria.list();
    }

    @Override
    public Boolean guardar(Amistad amistad) {
        Session session = sessionFactory.getCurrentSession();

        Criteria criteria = session.createCriteria(Amistad.class);
        criteria.add(Restrictions.eq("usuario", amistad.getUsuario()));
        criteria.add(Restrictions.eq("amigo", amistad.getAmigo()));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        Amistad notificacionExistente = (Amistad) criteria.uniqueResult();

        if (notificacionExistente == null) {
            session.saveOrUpdate(amistad);
            return true;
        }else {
            notificacionExistente.setEstado(amistad.getEstado());
            session.update(notificacionExistente);
            return true;
        }
    }

    @Override
    public List<Amistad> listarAmigosPorUsuario(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Amistad.class);

        criteria.add(Restrictions.or(
                Restrictions.eq("usuario.id", userId),
                Restrictions.eq("amigo.id", userId)
        ));
        criteria.add(Restrictions.eq("estado", "aceptada"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        List<Amistad> amistades = criteria.list();

        List<Amistad> amistadesFiltradas = new ArrayList<>();
        for (Amistad amistad : amistades) {

            if (amistad.getUsuario().getId().equals(userId)) {
                Amistad amistadFiltrada = new Amistad();
                amistadFiltrada.setAmigo(amistad.getAmigo());
                amistadesFiltradas.add(amistadFiltrada);
            }
            else if (amistad.getAmigo().getId().equals(userId)) {
                Amistad amistadFiltrada = new Amistad();
                amistadFiltrada.setUsuario(amistad.getUsuario());
                amistadesFiltradas.add(amistadFiltrada);
            }
        }

        return amistadesFiltradas;
    }

    @Override
    public Amistad buscarAmistadPorIdDeSolicitud(Long requestId) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Amistad.class);
        criteria.add(Restrictions.eq("id", requestId));
        return (Amistad) criteria.uniqueResult();
    }


}