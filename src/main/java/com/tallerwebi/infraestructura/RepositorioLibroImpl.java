package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.RepositorioLibro;
import com.tallerwebi.dominio.excepcion.LibroNoEncontrado;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioLibroImpl implements RepositorioLibro {

    SessionFactory sessionFactory;

    public RepositorioLibroImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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
        System.out.println("Actualizando libro con ID: " + libro.getId());
        sessionFactory.getCurrentSession().saveOrUpdate(libro);
    }

    @Override
    public String guardarLibro(Libro libro) throws LibroNoEncontrado {
        try{
            sessionFactory.getCurrentSession().save(libro);
        }catch (Exception e){
            throw new LibroNoEncontrado("error al guardar el libro con ID: " + libro.getId());

        }
        return "libro guardado";
    }


}
