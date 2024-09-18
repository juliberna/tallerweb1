package com.tallerwebi.infraestructura.repository;

import com.tallerwebi.dominio.model.Libro;
import com.tallerwebi.dominio.model.Review;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.management.Query;
import java.util.List;
import java.util.TreeSet;

@Repository("repositorioReview")
public class RepositorioReviewImpl implements RepositorioReview {

     SessionFactory sessionFactory;

    @Autowired
    public RepositorioReviewImpl(SessionFactory sessionFactory){

        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Review> getReviews() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Review.class).list();

    }

    @Override
    public void guardar(Review review) {
        sessionFactory.getCurrentSession().save(review);
    }
    @Override
    public void modificar(Review review) {
        sessionFactory.getCurrentSession().save(review);
    }



    @Override
    public List<Review> getReviewsDeAmigos() {
       /*
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Review.class).list();
        */
        return null;

    }

}
