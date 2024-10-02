package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.model.UsuarioLibro;
import com.tallerwebi.dominio.repository.RepositorioLibro;
import com.tallerwebi.dominio.repository.RepositorioUsuarioLibro;
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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioUsuarioLibroTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioLibro repositorioLibro;

    @Autowired
    private RepositorioUsuarioLibro repositorioUsuarioLibro;

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarYEncontrarUsuarioLibro() {
        // Creo y guardo un usuario de prueba
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        sessionFactory.getCurrentSession().save(usuario); // Guardo usuario

        // Creo y guardo un libro de prueba
        Libro libro = new Libro();
        libro.setId(1L);
        sessionFactory.getCurrentSession().save(libro); // Guardo libro

        // Creo un nuevo objeto UsuarioLibro
        UsuarioLibro usuarioLibro = new UsuarioLibro();
        usuarioLibro.setUsuario(usuario);
        usuarioLibro.setLibro(libro);
        usuarioLibro.setEstadoDeLectura("Leyendo");

        // Guardo el objeto en la base de datos
        repositorioUsuarioLibro.guardar(usuarioLibro);

        // Busco el objeto por usuario y libro
        UsuarioLibro encontrado = repositorioUsuarioLibro.encontrarUsuarioIdYLibroId(usuario.getId(), libro.getId());

        // Verifico que el objeto encontrado no es nulo
        assertThat(encontrado, is(notNullValue()));
        // Verifico que los IDs coinciden
        assertThat(encontrado.getUsuario().getId(), is(usuario.getId()));
        assertThat(encontrado.getLibro().getId(), is(libro.getId()));
    }





}
