package com.tallerwebi.infraestructura.repository;

import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.model.UsuarioLibro;
import com.tallerwebi.dominio.repository.RepositorioUsuario;
import com.tallerwebi.dominio.repository.RepositorioUsuarioLibro;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public List<UsuarioLibro> buscarPorEstadoDeLectura(String estadoDeLectura, Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();

        return session.createCriteria(UsuarioLibro.class)
                .add(Restrictions.eq("usuario", usuario))
                .add(Restrictions.eq("estadoDeLectura", estadoDeLectura))
                .list();
    }

    @Override
    public List<UsuarioLibro> buscarLibroPorId(Long idLibro) {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(UsuarioLibro.class)
                .add(Restrictions.eq("libro.id", idLibro))
                .list();
    }
}
