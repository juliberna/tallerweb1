package com.tallerwebi.infraestructura.service;

import com.tallerwebi.dominio.excepcion.ListaDeReviewsVacias;
import com.tallerwebi.dominio.excepcion.ListaVacia;
import com.tallerwebi.dominio.model.Review;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.dominio.repository.RepositorioReview;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioInicio")
@Transactional
public class ServicioInicioImpl implements ServicioInicio {

    private final RepositorioReview repositorioReview;

    public ServicioInicioImpl(RepositorioReview repositorioReview) {
        this.repositorioReview = repositorioReview;
    }

    @Override
    public void buscar(String nombre) {

    }

    @Override
    public List<Review> cargarTodasLasReviews() throws ListaDeReviewsVacias {
        List<Review> reviews = repositorioReview.getReviews();

        if (reviews.isEmpty()) {
            throw new ListaDeReviewsVacias("No hay comentarios disponibles");
        }


        return reviews;

    }

    @Override
    public void recomendarLibro(Usuario usuario) {



    }
}
