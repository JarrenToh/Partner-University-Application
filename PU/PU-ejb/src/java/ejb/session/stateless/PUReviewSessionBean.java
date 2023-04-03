/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PU;
import entity.PUReview;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jarrentoh
 */
@Stateless
public class PUReviewSessionBean implements PUReviewSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    //CREATE
    @Override
    public Long createPUReview(PUReview review, Long puId) {
        PU pu = em.find(PU.class, puId);
        //Link pu to puReview
        em.persist(review);
        review.setPu(pu);
        pu.getPuReviews().add(review);
        return review.getPuReviewId();
    }

    //RETRIEVE
    @Override
    public PUReview retrievePUReviewById(long id) {
        return em.find(PUReview.class, id);
    }

    @Override
    public List<PUReview> retrievePUReviewByPU(PU pu) {

        Query q = em.createQuery("SELECT r FROM PUReview r, PU pu WHERE r.pu = :p");
        q.setParameter("p", pu);
        return q.getResultList();
    }

    //UPDATE
    @Override
    public PUReview updatePUReview(PUReview review) {
        PUReview pUReviewToBeUpdated = em.find(PUReview.class, review.getPuReviewId());
        pUReviewToBeUpdated.setRating(review.getRating());
        pUReviewToBeUpdated.setReview(review.getReview());
        pUReviewToBeUpdated.setNoOfLikes(review.getNoOfLikes());
        pUReviewToBeUpdated.setNoOfDislikes(review.getNoOfDislikes());
        pUReviewToBeUpdated.setIsInappropriate(review.getIsInappropriate());
        return pUReviewToBeUpdated;
    }

    @Override
    public List<PUReview> retrieveReportedPUReview() {
        return em.createQuery("SELECT r FROM PUReview r WHERE r.isInappropriate = true").getResultList();
    }

    //DELETE
    @Override
    public Long deletePUReview(PUReview review) {
        PUReview r = em.find(PUReview.class, review.getPuReviewId());
        em.remove(r);
        return review.getPuReviewId();
    }

}
