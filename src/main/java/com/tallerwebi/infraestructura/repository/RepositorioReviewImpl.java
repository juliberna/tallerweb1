package com.tallerwebi.infraestructura.repository;


import com.tallerwebi.dominio.model.Review;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.TreeSet;

@Repository
public class RepositorioReviewImpl implements RepositorioReview {

     SessionFactory sessionFactory;

    @Autowired
    public RepositorioReviewImpl(SessionFactory sessionFactory){

        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Review> getReviews() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Review.class).addOrder(Order.asc("fechaPublicacion")).list();
        //TODO TEST
    }

    @Override
    public Review getReviewPorId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria review = session.createCriteria(Review.class);
        review.add(Restrictions.eq("id", id));
        return (Review) review.uniqueResult();

    }

    @Override
    public void guardar(Review review) {
        sessionFactory.getCurrentSession().save(review);
    }
    @Override
    public void modificar(Review review) {

        sessionFactory.getCurrentSession().saveOrUpdate(review);

        //TODO TEST
    }



    @Override
    public List<Review> getReviewsDeAmigos() {

        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Review.class).list();
        //TODO
        //AGREGAR USUARIO
        //USUARIO DEBE TENER AMIGOS (AGREGAR EN LA TABLA DE USUARIO)

    }

}
