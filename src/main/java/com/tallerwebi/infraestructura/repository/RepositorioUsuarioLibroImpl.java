package com.tallerwebi.infraestructura.repository;

import com.tallerwebi.dominio.model.UsuarioLibro;
import com.tallerwebi.dominio.repository.RepositorioUsuario;
import com.tallerwebi.dominio.repository.RepositorioUsuarioLibro;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioUsuarioLibroImpl implements RepositorioUsuarioLibro {

    public final SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioLibroImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public UsuarioLibro encontrarUsuarioIdYLibroId(Long usuarioId, Long libroId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(UsuarioLibro.class);


        criteria.add(Restrictions.eq("usuario.id", usuarioId));
        criteria.add(Restrictions.eq("libro.id", libroId));

        return (UsuarioLibro) criteria.uniqueResult();
    }

    @Override
    public void guardar(UsuarioLibro usuarioLibro) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(usuarioLibro);
    }
}
