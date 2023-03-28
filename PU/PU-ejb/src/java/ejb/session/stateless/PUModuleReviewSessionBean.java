/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FAQ;
import entity.PUModuleReview;
import entity.Student;
import error.NoResultException;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wayneonn
 */
@Stateless
public class PUModuleReviewSessionBean implements PUModuleReviewSessionBeanLocal {

    @PersistenceContext(unitName = "PU-ejbPU")
    private EntityManager em;

    @Override
    public void createPUModuleReview(PUModuleReview moduleReview) {
        em.persist(moduleReview);
        em.flush();
    }

    @Override
    public List<PUModuleReview> searchPUModuleReviewByReview(String review) {
        Query q;
        if (review != null) {
            q = em.createQuery("SELECT p FROM PUModuleReview p WHERE "
                    + "LOWER(p.review) LIKE :name");
            q.setParameter("name", "%" + review.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT p FROM PUModuleReview p");
        }

        return q.getResultList();
    } //end searchCustomers
    
    @Override
    public List<PUModuleReview> viewInappropiatePUModuleReview() {
        Query q;
            q = em.createQuery("SELECT p FROM PUModuleReview p WHERE p.isInappropriate = TRUE");

        return q.getResultList();
    } //end searchCustomers

    @Override
    public PUModuleReview getPUModuleReview(Long moduleReviewReviewId) throws NoResultException {
        PUModuleReview moduleReview = em.find(PUModuleReview.class, moduleReviewReviewId);
        
        if (moduleReview != null) {
            return moduleReview;
        } else {
            throw new NoResultException("Not found");
        }
    }

    @Override
    public List<PUModuleReview> retrieveAllPUModuleReviews() {
        Query query = em.createQuery("SELECT p FROM PUModuleReview p");
        return query.getResultList();
    }

    @Override
    public void updatePUModuleReview(Long moduleReviewReviewId, String review, Long rating, Integer noOfLikes, Integer noOfDislikes, Boolean isInappropiate) throws NoResultException {
        PUModuleReview moduleReview = getPUModuleReview(moduleReviewReviewId);

        moduleReview.setReview(review);
        moduleReview.setRating(rating);
        moduleReview.setNoOfLikes(noOfLikes);
        moduleReview.setNoOfDislikes(noOfDislikes);
        moduleReview.setIsInappropriate(isInappropiate);
    }

    @Override
    public void updatePUModuleReview(PUModuleReview p) throws NoResultException {
        PUModuleReview oldP = getPUModuleReview(p.getModuleReviewId());

        oldP.setReview(p.getReview());
        oldP.setRating(p.getRating());
        oldP.setNoOfLikes(p.getNoOfLikes());
        oldP.setNoOfDislikes(p.getNoOfDislikes());
        oldP.setIsInappropriate(p.getIsInappropriate());
    }

    @Override
    public void deletePUModuleReview(Long moduleReviewReviewId) throws NoResultException {
        PUModuleReview moduleReviewToRemove = getPUModuleReview(moduleReviewReviewId);
        em.remove(moduleReviewToRemove);
    }
    
    @Override
    public void toggleInappropiate(Long moduleReviewReviewId) throws NoResultException {
        PUModuleReview moduleReview = getPUModuleReview(moduleReviewReviewId);
        
        moduleReview.setIsInappropriate(!moduleReview.getIsInappropriate());
    }
}
