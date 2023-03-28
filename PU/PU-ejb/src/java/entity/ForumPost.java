/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class ForumPost implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    
    @Column(nullable = false)
    private String title;
    
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
    private ForumTopic forumTopic;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    @JsonbTransient
    private Student student;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "forumPost")
    private List<ForumComment> forumComments;
    
    public ForumPost() {
        
    }

    public ForumPost(String title, String message) {
        this.title = title;
        this.message = message;
        this.noOfLikes = 0;
        this.noOfDislikes = 0;
        this.isInappropriate = false;
        this.timeOfCreation = LocalDateTime.now();
        this.forumComments = new ArrayList();
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getPostId() != null ? getPostId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the postId fields are not set
        if (!(object instanceof ForumPost)) {
            return false;
        }
        ForumPost other = (ForumPost) object;
        if ((this.getPostId() == null && other.getPostId() != null) || (this.getPostId() != null && !this.postId.equals(other.postId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ForumPost[ id=" + getPostId() + " ]";
    }   
    
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
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
     * @return the forumTopic
     */
    public ForumTopic getForumTopic() {
        return forumTopic;
    }

    /**
     * @param forumTopic the forumTopic to set
     */
    public void setForumTopic(ForumTopic forumTopic) {
        this.forumTopic = forumTopic;
    }

    /**
     * @return the forumComments
     */
    public List<ForumComment> getForumComments() {
        return forumComments;
    }

    /**
     * @param forumComments the forumComments to set
     */
    public void setForumComments(List<ForumComment> forumComments) {
        this.forumComments = forumComments;
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
