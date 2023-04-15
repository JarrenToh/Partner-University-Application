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

/**
 *
 * @author wayneonn
 */
@Entity
public class PUModuleReview implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moduleReviewId;

    //private attributes
    @Column(columnDefinition = "VARCHAR(10000)")
    private String review;
    private Long rating;
    private Integer noOfLikes;
    private Integer noOfDislikes;
    private Boolean isInappropriate;

    @ManyToOne
    @JoinColumn(name = "moduleId")
    private PUModule module;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;

    @ManyToMany(mappedBy = "likedModReviews", fetch = FetchType.EAGER)
    private List<Student> studentsLiked;

    @ManyToMany(mappedBy = "dislikedModReviews", fetch = FetchType.EAGER)
    private List<Student> studentsDisliked;

    public PUModuleReview(String review, Long rating, Integer noOfLikes, Integer noOfDislikes) {
        this();
        this.review = review;
        this.rating = rating;
        this.noOfLikes = noOfLikes;
        this.noOfDislikes = noOfDislikes;
        this.isInappropriate = false;
    }

    public PUModuleReview() {
        this.studentsLiked = new ArrayList<>();
        this.studentsDisliked = new ArrayList<>();
    }

    public Long getModuleReviewId() {
        return moduleReviewId;
    }

    public void setModuleReviewId(Long moduleReviewId) {
        this.moduleReviewId = moduleReviewId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (moduleReviewId != null ? moduleReviewId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the moduleReviewId fields are not set
        if (!(object instanceof PUModuleReview)) {
            return false;
        }
        PUModuleReview other = (PUModuleReview) object;
        if ((this.moduleReviewId == null && other.moduleReviewId != null) || (this.moduleReviewId != null && !this.moduleReviewId.equals(other.moduleReviewId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PUModuleReview[ id=" + moduleReviewId + " ]";
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
     * @return the module
     */
    @JsonbTransient
    public PUModule getModule() {
        return module;
    }

    /**
     * @param module the module to set
     */
    public void setModule(PUModule module) {
        this.module = module;
    }

    /**
     * @return the students
     */
    @JsonbTransient
    public Student getStudent() {
        return student;
    }

    /**
     * @param students the students to set
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    @JsonbTransient
    public List<Student> getStudentsLiked() {
        return studentsLiked;
    }

    public void setStudentsLiked(List<Student> studentsLiked) {
        this.studentsLiked = studentsLiked;
    }

    @JsonbTransient
    public List<Student> getStudentsDisliked() {
        return studentsDisliked;
    }

    public void setStudentsDisliked(List<Student> studentsDisliked) {
        this.studentsDisliked = studentsDisliked;
    }

}
