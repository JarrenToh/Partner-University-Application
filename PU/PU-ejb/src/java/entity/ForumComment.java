/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.json.bind.annotation.JsonbTransient;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author kathleen
 */
@Entity
public class ForumComment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    
    @Column(nullable = false)
    private String message;
    
    @Column(nullable = false)
    private Integer noOfLikes;
    
    @Column(nullable = false)
    private Integer noOfDislikes;
    
    @Column(nullable = false)
    private Boolean isInappropriate;
    
    @Column(nullable = false)
    private LocalDateTime timeOfCreation;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    @JsonbTransient
    private ForumPost forumPost;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    @JsonbTransient
    private Student student;
    
    
    public ForumComment() {
        
    }

    public ForumComment(String message) {
        this.noOfLikes = 0;
        this.noOfDislikes = 0;
        this.isInappropriate = false;
        this.timeOfCreation = LocalDateTime.now();
        this.message = message;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commentId != null ? commentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the commentId fields are not set
        if (!(object instanceof ForumComment)) {
            return false;
        }
        ForumComment other = (ForumComment) object;
        if ((this.commentId == null && other.commentId != null) || (this.commentId != null && !this.commentId.equals(other.commentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ForumComment[ id=" + commentId + " ]";
    }   
    
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
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
     * @return the forumPost
     */
    public ForumPost getForumPost() {
        return forumPost;
    }

    /**
     * @param forumPost the forumPost to set
     */
    public void setForumPost(ForumPost forumPost) {
        this.forumPost = forumPost;
    }
    
    
    /**
     * @return the timeOfCreation
     */
    public LocalDateTime getTimeOfCreation() {
        return timeOfCreation;
    }

    /**
     * @param timeOfCreation the timeOfCreation to set
     */
    public void setTimeOfCreation(LocalDateTime timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    /**
     * @return the student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * @param student the student to set
     */
    public void setStudent(Student student) {
        this.student = student;
    }
    
}
