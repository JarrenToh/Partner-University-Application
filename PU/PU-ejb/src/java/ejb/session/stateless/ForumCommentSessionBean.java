/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ForumComment;
import entity.ForumPost;
import entity.Student;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author kathleen
 */
@Stateless
public class ForumCommentSessionBean implements ForumCommentSessionBeanLocal {

    @EJB(name = "ForumPostSessionBeanLocal")
    private ForumPostSessionBeanLocal forumPostSessionBeanLocal;

    @PersistenceContext(unitName = "PU-ejbPU")
    private EntityManager em;   

    @Override
    public void createNewForumComment(ForumComment forumComment, Long forumPostId, Long studentId) {
        ForumPost forumPost = em.find(ForumPost.class, forumPostId);
        Student student = em.find(Student.class, studentId);
        student.getComments().add(forumComment);
        forumComment.setStudent(student);
        forumPost.getForumComments().add(forumComment);
        forumComment.setForumPost(forumPost);
        forumComment.setTimeOfCreation(LocalDateTime.now());
        em.persist(forumComment);
        em.flush();
    }

    @Override
    public void updateForumComment(ForumComment forumComment) {
        
        ForumComment oldComment = retrieveForumCommentById(forumComment.getCommentId());

        oldComment.setMessage(forumComment.getMessage());
        oldComment.setNoOfLikes(forumComment.getNoOfLikes());
        oldComment.setNoOfDislikes(forumComment.getNoOfDislikes());
        oldComment.setIsInappropriate(forumComment.getIsInappropriate());
        
    }

    @Override
    public void deleteForumComment(Long forumCommentId) {
        ForumComment forumComment = em.find(ForumComment.class, forumCommentId);
        ForumPost forumPost = forumComment.getForumPost();
        forumPost.getForumComments().remove(forumComment);
        forumPostSessionBeanLocal.updateForumPost(forumPost);
        em.remove(forumComment);
        em.flush();
    }

    @Override
    public List<ForumComment> retrieveAllForumComments() {
        Query query = em.createQuery("SELECT c FROM ForumComment c");
        return query.getResultList();
    }

    @Override
    public ForumComment retrieveForumCommentById(Long forumCommentId) {
        ForumComment forumComment = em.find(ForumComment.class, forumCommentId);
        return forumComment;
    }
    
    @Override
    public void reportForumComment(Long forumCommentId) {
        ForumComment forumComment = em.find(ForumComment.class, forumCommentId);
        
        forumComment.setIsInappropriate(true);
    }
    
    public void resolveForumComment(Long forumCommentId) {
        ForumComment forumComment = em.find(ForumComment.class, forumCommentId);
        
        forumComment.setIsInappropriate(false);
    }

    @Override
    public void likeForumComment(Long forumCommentId) {
        ForumComment forumComment = em.find(ForumComment.class, forumCommentId);
        
        forumComment.setNoOfLikes(forumComment.getNoOfLikes() + 1);
    }

    @Override
    public void unlikeForumComment(Long forumCommentId) {
        ForumComment forumComment = em.find(ForumComment.class, forumCommentId);
        
        forumComment.setNoOfLikes(forumComment.getNoOfLikes() - 1);
    }

    @Override
    public void dislikeForumComment(Long forumCommentId) {
        ForumComment forumComment = em.find(ForumComment.class, forumCommentId);
        
        forumComment.setNoOfDislikes(forumComment.getNoOfDislikes() + 1);
    }

    @Override
    public void undislikeForumComment(Long forumCommentId) {
        ForumComment forumComment = em.find(ForumComment.class, forumCommentId);
        
        forumComment.setNoOfDislikes(forumComment.getNoOfDislikes() - 1);
    }

}
