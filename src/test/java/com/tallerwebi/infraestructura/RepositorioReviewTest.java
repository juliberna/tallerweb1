package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.model.*;

import com.tallerwebi.dominio.repository.RepositorioLibro;
import com.tallerwebi.infraestructura.repository.RepositorioReview;

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
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import javax.transaction.Transactional;

import static org.hamcrest.Matchers.notNullValue;


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

        MatcherAssert.assertThat(comentario.getId(), notNullValue());
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
        review.setTextoComentario(texto);
        review.setUsuario(usuario);
        review.setRating(estrellas);
        review.setLibro(libro);

        //when
        sessionFactory.getCurrentSession().save(usuario);
        sessionFactory.getCurrentSession().save(libro);
        sessionFactory.getCurrentSession().save(usuario);
        repositorioReview.guardar(review);
       Review reviewEncontrada = repositorioReview.getReviewPorId(review.getId());

        //then
        MatcherAssert.assertThat(reviewEncontrada, notNullValue());


    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarTresReviews() {
        Usuario usuario = new Usuario();
        String texto = "contenidoPublicacion";
        Rating estrellas = Rating.TRES_ESTRELLAS;
        Libro libro = new Libro();
        libro.setTitulo("Harry Potter");
        libro.setAutor("J.K. Rowling");

        Review review = new Review();
        review.setTextoComentario(texto);
        review.setUsuario(usuario);
        review.setRating(estrellas);
        review.setLibro(libro);

        Review review2 = new Review();
        review2.setTextoComentario(texto);
        review2.setUsuario(usuario);
        review2.setRating(estrellas);
        review2.setLibro(libro);

        Review review3 = new Review();
        review3.setTextoComentario(texto);
        review3.setUsuario(usuario);
        review3.setRating(estrellas);
        review3.setLibro(libro);

        //when
        sessionFactory.getCurrentSession().save(usuario);
        sessionFactory.getCurrentSession().save(libro);
        sessionFactory.getCurrentSession().save(usuario);
        repositorioReview.guardar(review);
        repositorioReview.guardar(review2);
        repositorioReview.guardar(review3);
        Review reviewEncontrada = repositorioReview.getReviewPorId(review.getId());
        Review reviewEncontrada2 = repositorioReview.getReviewPorId(review2.getId());
        Review reviewEncontrada3 = repositorioReview.getReviewPorId(review3.getId());

        //then
        MatcherAssert.assertThat(reviewEncontrada, notNullValue());
        MatcherAssert.assertThat(reviewEncontrada2, notNullValue());
        MatcherAssert.assertThat(reviewEncontrada3, notNullValue());

    }

}
