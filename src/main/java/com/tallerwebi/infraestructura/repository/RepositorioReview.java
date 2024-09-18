package com.tallerwebi.infraestructura.repository;

import com.tallerwebi.dominio.model.Review;

import java.util.List;


public interface RepositorioReview {

    public List<Review> getReviews();

    public List<Review> getReviewsDeAmigos();

    public void guardar(Review review);

    public void modificar(Review review);
}
