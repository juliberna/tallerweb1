package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.model.Comentario;
import com.tallerwebi.dominio.model.Rating;
import com.tallerwebi.dominio.model.Review;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.infraestructura.repository.RepositorioReviewImpl;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})


public class RepositorioReviewTest {


@Autowired
SessionFactory sessionFactory;

@Autowired
RepositorioReviewImpl repositorioReview;

    @Test
    public void queSePuedaCrearUnComentario() {
        Usuario usuario = new Usuario();
        String texto = "ComentarioContenido";

        Comentario comentario = new Comentario(usuario, texto);

        assertNotNull(comentario);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnComentario() {
        Usuario usuario = new Usuario();
        String texto = "contenidoPublicacion";
        Rating estrellas = Rating.TRES_ESTRELLAS;

        Review review = new Review(usuario, texto, estrellas);

        //when
        repositorioReview.guardar(review);

        //then
        assertThat(review.getId(), notNullValue());


    }

}
