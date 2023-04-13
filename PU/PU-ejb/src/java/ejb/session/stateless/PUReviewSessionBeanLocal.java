/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PU;
import entity.PUReview;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jarrentoh
 */
@Local
public interface PUReviewSessionBeanLocal {

    Long createPUReview(PUReview review, Long puId, Long studentId);

    PUReview retrievePUReviewById(long id);

    List<PUReview> retrievePUReviewByPU(PU pu);
    
    List<PUReview> retrieveAllPUReview(); 

    PUReview updatePUReview(PUReview review);

    Long deletePUReview(PUReview review);

    List<PUReview> retrieveReportedPUReview();
    
    Double retrieveRating(Long puId);

    Long updatePUReviewLikedByStudent(Long puReviewId, Long studentId, Integer choice);
    
    Long updatePUReviewDislikedByStudent(Long puReviewId, Long studentId, Integer choice);
    
}
