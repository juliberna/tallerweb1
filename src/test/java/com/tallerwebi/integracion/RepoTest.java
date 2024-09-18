package com.tallerwebi.integracion;

import com.tallerwebi.dominio.Libro;
import com.tallerwebi.infraestructura.RepositorioLibroImpl;
import com.tallerwebi.infraestructura.ServicioLibroImpl;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import com.tallerwebi.dominio.RepositorioLibro;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepoTest {

    @InjectMocks
    private ServicioLibroImpl servicioLibro;

    @Mock
    private RepositorioLibro repositorioLibro;

    @Mock
    private SessionFactory sessionFactory;

    @Before
    public void setUp() {
        repositorioLibro = mock(RepositorioLibro.class);
        sessionFactory = mock(SessionFactory.class);
        servicioLibro = new ServicioLibroImpl(repositorioLibro);
        System.out.println("ServicioLibroImpl: " + servicioLibro);
        System.out.println("SessionFactory: " + sessionFactory);

    }

    @Test
    public void deberiaGuardarLibroCorrectamente() {
        Libro libro1 = new Libro();
        libro1.setId(60L);
        libro1.setTitulo("Harry Potter");
        libro1.setAutor("JK Rowling");
        libro1.setEditorial("Editorial Sudamericana");
        libro1.setIsbn("978-0307474728");
        libro1.setEstadoDeLectura("leído");
        libro1.setDescripcion("Una obra maestra de la literatura latinoamericana.");
        libro1.setGenero("Realismo mágico");
        libro1.setPuntuacion(5);
        libro1.setImagenUrl("https://example.com/imagen.jpg");
        libro1.setReseña("Un libro fascinante que explora la historia de la familia Buendía.");

        servicioLibro.actualizarLibro(libro1);

    }
}
