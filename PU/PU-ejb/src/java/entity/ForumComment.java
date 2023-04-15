/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.json.bind.annotation.JsonbTransient;
import java.util.List;
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
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "parentComment")
    private List<ForumComment> replies;
    
    @ManyToOne(optional = true)
    @JoinColumn(nullable = true)
    @JsonbTransient
    private ForumComment parentComment;
    
    @Column(nullable = false)
    private Long studentId;
    
    @Column(nullable = false)
    private String studentFirstName;
    
    @Column(nullable = false)
    private String studentLastName;
    
    @Column(nullable = false)
    private Boolean isEdited;
    
    @Column(nullable = true)
    private LocalDateTime lastEdit;
    
    @Column(nullable = false)
    private List<Long> likedStudents;
    
    @Column(nullable = false)
    private List<Long> dislikedStudents;
    
    @Column(nullable = false)
    private Boolean isAReply;
    
    @Column(nullable = false)
    private Boolean showReplies;
    
    public ForumComment() {
        this.timeOfCreation = LocalDateTime.now();
        this.likedStudents = new ArrayList();
        this.dislikedStudents = new ArrayList();
        this.replies = new ArrayList();
        this.noOfLikes = 0;
        this.noOfDislikes = 0;
        this.isInappropriate = false;
        this.isEdited = false;
        this.isAReply = false;
        this.showReplies = false;
        this.replies = new ArrayList();
    }

    public ForumComment(String message, Boolean isAReply) {
        this.timeOfCreation = LocalDateTime.now();
        this.likedStudents = new ArrayList();
        this.dislikedStudents = new ArrayList();
        this.replies = new ArrayList();
        this.noOfLikes = 0;
        this.noOfDislikes = 0;
        this.isInappropriate = false;
        this.isEdited = false;
        this.isAReply = isAReply;
        this.message = message;
        this.showReplies = false;
        this.replies = new ArrayList();
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
    @JsonbTransient
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
    

    /**
     * @return the replies
     */
    public List<ForumComment> getReplies() {
        return replies;
    }

    /**
     * @param replies the replies to set
     */
    public void setReplies(List<ForumComment> replies) {
        this.replies = replies;
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
     * @return the likedStudents
     */
    public List<Long> getLikedStudents() {
        return likedStudents;
    }

    /**
     * @param likedStudents the likedStudents to set
     */
    public void setLikedStudents(List<Long> likedStudents) {
        this.likedStudents = likedStudents;
    }

    /**
     * @return the dislikedStudents
     */
    public List<Long> getDislikedStudents() {
        return dislikedStudents;
    }

    /**
     * @param dislikedStudents the dislikedStudents to set
     */
    public void setDislikedStudents(List<Long> dislikedStudents) {
        this.dislikedStudents = dislikedStudents;
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
     * @return the isAReply
     */
    public Boolean getIsAReply() {
        return isAReply;
    }

    /**
     * @param isAReply the isAReply to set
     */
    public void setIsAReply(Boolean isAReply) {
        this.isAReply = isAReply;
    }   
    
    /**
     * @return the showReplies
     */
    public Boolean getShowReplies() {
        return showReplies;
    }

    /**
     * @param showReplies the showReplies to set
     */
    public void setShowReplies(Boolean showReplies) {
        this.showReplies = showReplies;
    }
    
    /**
     * @return the parentComment
     */
    public ForumComment getParentComment() {
        return parentComment;
    }

    /**
     * @param parentComment the parentComment to set
     */
    public void setParentComment(ForumComment parentComment) {
        this.parentComment = parentComment;
    }

}
