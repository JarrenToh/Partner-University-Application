/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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

    @Column(nullable = true, columnDefinition = "VARCHAR(10000)")
    private String review;
    private Integer noOfLikes;
    private Integer noOfDislikes;
    private Boolean isInappropriate;

    @ManyToOne
    @JoinColumn(name = "pu_puid", nullable = false)
    private PU pu;

    @OneToOne
    @JoinColumn(name = "student_studentId", nullable = false)
    private Student student;

    @ManyToMany(mappedBy = "likedPUReviews",fetch = FetchType.EAGER)
    private List<Student> studentsLiked;

    @ManyToMany(mappedBy = "dislikedPUReviews", fetch = FetchType.EAGER)
    private List<Student> studentsDisliked;

    public PUReview() {
        this.noOfLikes = 0;
        this.noOfDislikes = 0;
        this.isInappropriate = false;
        this.studentsLiked = new ArrayList<>();
        this.studentsDisliked = new ArrayList<>();
    }

    //not needed
    public PUReview(Long rating, Long puReviewId) {
        this();
        this.puReviewId = puReviewId;
        this.rating = rating;
    }

    public PUReview(Long rating, String review) {
        this();
        this.rating = rating;
        this.review = review;

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
    @JsonbTransient
    public PU getPu() {
        return pu;
    }

    /**
     * @param pu the pu to set
     */
    public void setPu(PU pu) {
        this.pu = pu;
    }

    /**
     * @return the student
     */
    @JsonbTransient
    public Student getStudent() {
        return student;
    }

    /**
     * @param student the student to set
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * @return the studentsLiked
     */
    @JsonbTransient
    public List<Student> getStudentsLiked() {
        return studentsLiked;
    }

    /**
     * @param studentsLiked the studentsLiked to set
     */
    public void setStudentsLiked(List<Student> studentsLiked) {
        this.studentsLiked = studentsLiked;
    }

    /**
     * @return the studentsDisliked
     */
    @JsonbTransient
    public List<Student> getStudentsDisliked() {
        return studentsDisliked;
    }

    /**
     * @param studentsDisliked the studentsDisliked to set
     */
    public void setStudentsDisliked(List<Student> studentsDisliked) {
        this.studentsDisliked = studentsDisliked;
    }
}
