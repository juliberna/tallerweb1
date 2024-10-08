package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.Review;

import java.util.List;


public interface RepositorioReview {

    public List<Review> getReviews();

    public List<Review> getReviewsDeAmigos();

    public Review getReviewPorId(Long id);

    public void guardar(Review review);

    public void modificar(Review review);
}
