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
public class ForumTopic implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topicId;
    
    @Column(nullable = false)
    private String topicName;
    
    @Column(nullable = false)
    private Boolean isInappropriate;
    
    @Column(nullable = false)
    private LocalDateTime timeOfCreation;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "forumTopic")
    private List<ForumPost> forumPosts;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    @JsonbTransient
    private Student student;
    
    public ForumTopic() {
        
    }

    public ForumTopic(String topicName) {
        this.topicName = topicName;
        this.isInappropriate = false;
        this.timeOfCreation = LocalDateTime.now();
        this.forumPosts = new ArrayList();
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (topicId != null ? topicId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the topicId fields are not set
        if (!(object instanceof ForumTopic)) {
            return false;
        }
        ForumTopic other = (ForumTopic) object;
        if ((this.topicId == null && other.topicId != null) || (this.topicId != null && !this.topicId.equals(other.topicId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ForumTopic[ id=" + topicId + " ]";
    }
       
    /**
     * @return the topicName
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * @param topicName the topicName to set
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName;
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
     * @return the forumPosts
     */
    public List<ForumPost> getForumPosts() {
        return forumPosts;
    }

    /**
     * @param forumPosts the forumPosts to set
     */
    public void setForumPosts(List<ForumPost> forumPosts) {
        this.forumPosts = forumPosts;
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

}
