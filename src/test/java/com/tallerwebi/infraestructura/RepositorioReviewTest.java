package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.model.*;

import com.tallerwebi.infraestructura.repository.RepositorioReview;

import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.junit.Assert;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import javax.transaction.Transactional;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})


public class RepositorioReviewTest {


    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioReview repositorioReview;


    @Test
    public void queSePuedaCrearUnComentario() {
        Usuario usuario = new Usuario();
        String texto = "ComentarioContenido";

        Comentario comentario = new Comentario();
        comentario.setUsuario(usuario);
        comentario.setTextoComentario(texto);

        assertNotNull(comentario);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnaReview() {
        Usuario usuario = new Usuario();
        String texto = "contenidoPublicacion";
        Rating estrellas = Rating.TRES_ESTRELLAS;
        Libro libro = new Libro();
        libro.setTitulo("Harry Potter");
        libro.setAutor("J.K. Rowling");

        Review review = new Review();
        review.setUsuario(usuario);
        review.setRating(estrellas);
        review.setLibro(libro);

        //when
        sessionFactory.getCurrentSession().save(review);
        //then
        assertNotNull(review.getId());


    }

}
