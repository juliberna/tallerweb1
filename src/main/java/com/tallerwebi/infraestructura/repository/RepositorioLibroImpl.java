package com.tallerwebi.infraestructura.repository;

import com.tallerwebi.dominio.excepcion.LibroNoEncontrado;
import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.dominio.repository.RepositorioLibro;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioLibroImpl implements RepositorioLibro {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioLibroImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Libro> buscar(String query) {
        Session session = sessionFactory.getCurrentSession();
        //TODO agregar busqueda por genero
        return session.createCriteria(Libro.class)
                .add(Restrictions.disjunction()
                        .add(Restrictions.ilike("titulo", query, MatchMode.ANYWHERE))
                        .add(Restrictions.ilike("autor", query, MatchMode.ANYWHERE)))
                .list();
    }

    @Override
    public Libro buscarLibro(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Libro libro = session.get(Libro.class, id);
            if (libro == null) {
                throw new LibroNoEncontrado("Libro no encontrado con ID: " + id);
            }
            return libro;
        } catch (Exception e) {
            throw new LibroNoEncontrado("Error al buscar el libro con ID: " + id);
        }
    }

    @Override
    public void actualizarLibro(Libro libro) {
        sessionFactory.getCurrentSession().saveOrUpdate(libro);
    }

    @Override
    public List<Libro> buscarPorEstadoDeLectura(String estadoDeLectura) {
        Session session = sessionFactory.getCurrentSession();

        return session.createCriteria(Libro.class)
                .add(Restrictions.eq("estadoDeLectura", estadoDeLectura))
                .list();
    }

}
