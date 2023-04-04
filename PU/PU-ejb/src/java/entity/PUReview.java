/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
<<<<<<< Updated upstream
=======
>>>>>>> Stashed changes

/**
 *
 * @author jarrentoh
 */
@Entity
public class PUReview implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long puReviewId;
    
    private Long rating;
    private String review;
    private Integer noOfLikes;
    private Integer noOfDislikes;
    private Boolean isInappropriate;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private PU pu;

<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
    public PUReview() {
    }

    public PUReview(Long rating, Long puReviewId) {
        this.puReviewId = puReviewId;
        this.rating = rating;
    }

    public PUReview(Long rating, String review, Integer noOfLikes, Integer noOfDislikes, Boolean isInappropriate) {
        this.rating = rating;
        this.review = review;
        this.noOfLikes = noOfLikes;
        this.noOfDislikes = noOfDislikes;
        this.isInappropriate = isInappropriate;
    }

    public Long getPuReviewId() {
        return puReviewId;
    }

    public void setPuReviewId(Long puReviewId) {
        this.puReviewId = puReviewId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (puReviewId != null ? puReviewId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the puReviewId fields are not set
        if (!(object instanceof PUReview)) {
            return false;
        }
        PUReview other = (PUReview) object;
        if ((this.puReviewId == null && other.puReviewId != null) || (this.puReviewId != null && !this.puReviewId.equals(other.puReviewId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PUReview[ id=" + puReviewId + " ]";
    }

    /**
     * @return the rating
     */
    public Long getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(Long rating) {
        this.rating = rating;
    }

    /**
     * @return the review
     */
    public String getReview() {
        return review;
    }

    /**
     * @param review the review to set
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     * @return the noOfLikes
     */
    public Integer getNoOfLikes() {
        return noOfLikes;
    }

    /**
     * @param noOfLikes the noOfLikes to set
     */
    public void setNoOfLikes(Integer noOfLikes) {
        this.noOfLikes = noOfLikes;
    }

    /**
     * @return the noOfDislikes
     */
    public Integer getNoOfDislikes() {
        return noOfDislikes;
    }

    /**
     * @param noOfDislikes the noOfDislikes to set
     */
    public void setNoOfDislikes(Integer noOfDislikes) {
        this.noOfDislikes = noOfDislikes;
    }

    /**
     * @return the isInappropriate
     */
    public Boolean getIsInappropriate() {
        return isInappropriate;
    }

    /**
     * @param isInappropriate the isInappropriate to set
     */
    public void setIsInappropriate(Boolean isInappropriate) {
        this.isInappropriate = isInappropriate;
    }

    /**
     * @return the pu
     */
    public PU getPu() {
        return pu;
    }

    /**
     * @param pu the pu to set
     */
    public void setPu(PU pu) {
        this.pu = pu;
    }
    
}
