/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FAQ;
import entity.PUModule;
import entity.PUModuleReview;
import entity.Student;
import error.NoResultException;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wayneonn
 */
@Local
public interface PUModuleReviewSessionBeanLocal {

    public void createPUModuleReview(PUModuleReview moduleReview);

    public List<PUModuleReview> retrieveAllPUModuleReviews();

    public void updatePUModuleReview(Long moduleReviewReviewId, String review, Long rating, Integer noOfLikes, Integer noOfDislikes, Boolean isInappropiate) throws NoResultException;
    
    public void deletePUModuleReview(Long moduleReviewReviewId) throws NoResultException;

    public List<PUModuleReview> searchPUModuleReviewByReview(String review);

    public PUModuleReview getPUModuleReview(Long moduleReviewReviewId) throws NoResultException;

    public void updatePUModuleReview(PUModuleReview p) throws NoResultException;

    public void toggleInappropiate(Long moduleReviewReviewId) throws NoResultException;

    public List<PUModuleReview> viewInappropiatePUModuleReview();
}
