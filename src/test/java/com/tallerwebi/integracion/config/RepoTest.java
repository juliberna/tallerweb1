package com.tallerwebi.integracion.config;

import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.RepositorioLibro;
import com.tallerwebi.infraestructura.RepositorioLibroImpl;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepoTest {

    @Mock
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioLibroImpl repositorioLibro;

    @Before
    public void setUp() {
        repositorioLibro = mock(RepositorioLibroImpl.class);
        sessionFactory = mock(SessionFactory.class);
    }

  @Test
    public void test() {
      Libro libro1 = new Libro();
      libro1.setId(1L); // Para pruebas, puedes asignar un ID directamente
      libro1.setTitulo("Cien años de soledad");
      libro1.setAutor("Gabriel García Márquez");
      libro1.setEditorial("Editorial Sudamericana");
      libro1.setIsbn("978-0307474728");
      libro1.setEstadoDeLectura("leído");
      libro1.setDescripcion("Una obra maestra de la literatura latinoamericana.");
      libro1.setGenero("Realismo mágico");
      libro1.setPuntuacion(5); // Puntuación de 1 a 5
      libro1.setImagenUrl("https://example.com/imagen.jpg");
      libro1.setReseña("Un libro fascinante que explora la historia de la familia Buendía.");

      String respuesta = repositorioLibro.guardarLibro(libro1);
      System.out.println(respuesta);

  }


}
