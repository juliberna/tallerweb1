package com.tallerwebi.infraestructura.repository;

import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.model.UsuarioLibro;
import com.tallerwebi.dominio.repository.RepositorioUsuarioLibro;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    @Override
    public List<UsuarioLibro> buscarLibrosLeidosPorAño(Integer anio, Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();

        // Definir el rango de fechas para el año actual
        // 1 de enero del año actual
        LocalDate inicioAnio = LocalDate.of(anio, 1, 1);
        // 31 de diciembre del año actual
        LocalDate finAnio = LocalDate.of(anio, 12, 31);

        // Compara el campo anioLeido con el rango de fechas (inicioAnio, finAnio),
        // devuelve los libros que fueron leídos durante ese año.
        return session.createCriteria(UsuarioLibro.class)
                .add(Restrictions.eq("usuario", usuario))
                .add(Restrictions.eq("estadoDeLectura", "Leido"))
                .add(Restrictions.between("fechaLeido", inicioAnio, finAnio))
                .list();
    }
}
