package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.dominio.repository.RepositorioLibro;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioLibroTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioLibro repositorioLibro;

    @Test
    @Transactional
    @Rollback
    public void puedoBuscarLibros() {
        //debo poder crear y  guardar libros
        Libro libro1 = new Libro();
        libro1.setTitulo("Harry potter 1");
        sessionFactory.getCurrentSession().save(libro1);

        Libro libro2 = new Libro();
        libro2.setTitulo("Harry potter 2");
        sessionFactory.getCurrentSession().save(libro2);

        Libro libro3 = new Libro();
        libro3.setTitulo("Test");
        sessionFactory.getCurrentSession().save(libro2);

        //buscarlos y comparar los resultados con los libros guardados
        List<Libro> librosObtenidos = repositorioLibro.buscar("harry potter");

        assertThat(librosObtenidos,is(not(empty())));
        assertThat(librosObtenidos.size(),is(2));
        assertThat(librosObtenidos.get(0).getTitulo(),equalTo("Harry potter 1"));
    }

}
