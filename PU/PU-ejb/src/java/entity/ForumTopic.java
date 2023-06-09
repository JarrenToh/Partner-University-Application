/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
    
    @Column(nullable = true)
    private String topicName;
    
    @Column(nullable = true)
    private Boolean isInappropriate;
    
    @Column(nullable = true)
    private LocalDateTime timeOfCreation;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "forumTopic")
    private List<ForumPost> forumPosts;
    
    @ManyToOne(optional = true)
    @JoinColumn(nullable = true)
    @JsonbTransient
    private Student student;
  
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    @JsonbTransient
    private PU pu;
    
    @Column(nullable = true)
    private Long studentId;
    
    @Column(nullable = true)
    private String studentFirstName;
    
    @Column(nullable = true)
    private String studentLastName;
    
    @Column(nullable = true)
    private String puName;
    
    @Column(nullable = true)
    private Boolean isEdited;
    
    @Column(nullable = true)
    private LocalDateTime lastEdit;
    
    @ManyToOne(optional = true)
    @JoinColumn(nullable = true, name = "created_by_admin")
    private NUSchangeAdmin admin;
    
    public ForumTopic() {
        this.timeOfCreation = LocalDateTime.now();
        this.forumPosts = new ArrayList();
        this.isInappropriate = false;
        this.isEdited = false;
    }

    public ForumTopic(String topicName) {
        this.topicName = topicName;
        this.isInappropriate = false;
        this.timeOfCreation = LocalDateTime.now();
        this.forumPosts = new ArrayList();
        this.isEdited = false;
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
    
    /**
     * @return the studentId
     */
    public Long getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    
    /**
     * @return the studentFirstName
     */
    public String getStudentFirstName() {
        return studentFirstName;
    }

    /**
     * @param studentFirstName the studentFirstName to set
     */
    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    /**
     * @return the studentLastName
     */
    public String getStudentLastName() {
        return studentLastName;
    }

    /**
     * @param studentLastName the studentLastName to set
     */
    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }    
    
    /**
     * @return the isEdited
     */
    public Boolean getIsEdited() {
        return isEdited;
    }

    /**
     * @param isEdited the isEdited to set
     */
    public void setIsEdited(Boolean isEdited) {
        this.isEdited = isEdited;
    }

    /**
     * @return the lastEdit
     */
    public LocalDateTime getLastEdit() {
        return lastEdit;
    }

    /**
     * @param lastEdit the lastEdit to set
     */
    public void setLastEdit(LocalDateTime lastEdit) {
        this.lastEdit = lastEdit;
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
    
    /**
     * @return the puName
     */
    public String getPuName() {
        return puName;
    }

    /**
     * @param puName the puName to set
     */
    public void setPuName(String puName) {
        this.puName = puName;
    }

    public NUSchangeAdmin getAdmin() {
        return admin;
    }

    public void setAdmin(NUSchangeAdmin admin) {
        this.admin = admin;
    }

}
