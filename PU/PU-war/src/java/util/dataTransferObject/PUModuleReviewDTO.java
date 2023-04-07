/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dataTransferObject;

/**
 *
 * @author wjahoward
 */
public class PUModuleReviewDTO {
    private long moduleReviewId;
    private Long rating;
    private String review;
    private Integer noOfLikes;
    private Integer noOfDislikes;
    private Boolean isInappropriate;
    private Long studentId;
    private String studentFirstName;
    private String studentLastName;

    public PUModuleReviewDTO(long moduleReviewId, String review, Boolean isInappropriate, Long studentId, String studentFirstName, String studentLastName) {
        this.moduleReviewId = moduleReviewId;
        this.review = review;
        this.isInappropriate = isInappropriate;
        this.studentId = studentId;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
    }

    public PUModuleReviewDTO(long moduleReviewId, Long rating, String review, Integer noOfLikes, Integer noOfDislikes, Boolean isInappropriate, Long studentId, String studentFirstName, String studentLastName) {
        this.moduleReviewId = moduleReviewId;
        this.rating = rating;
        this.review = review;
        this.noOfLikes = noOfLikes;
        this.noOfDislikes = noOfDislikes;
        this.isInappropriate = isInappropriate;
        this.studentId = studentId;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
    }
    
    

    public long getModuleReviewId() {
        return moduleReviewId;
    }

    public void setModuleReviewId(long moduleReviewId) {
        this.moduleReviewId = moduleReviewId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Integer getNoOfLikes() {
        return noOfLikes;
    }

    public void setNoOfLikes(Integer noOfLikes) {
        this.noOfLikes = noOfLikes;
    }

    public Integer getNoOfDislikes() {
        return noOfDislikes;
    }

    public void setNoOfDislikes(Integer noOfDislikes) {
        this.noOfDislikes = noOfDislikes;
    }

    public Boolean getIsInappropriate() {
        return isInappropriate;
    }

    public void setIsInappropriate(Boolean isInappropriate) {
        this.isInappropriate = isInappropriate;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }
    
    
}
