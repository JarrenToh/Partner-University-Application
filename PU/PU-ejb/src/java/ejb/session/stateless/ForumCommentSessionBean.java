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
import java.util.ArrayList;
import java.util.Iterator;
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
    public ForumComment createNewForumComment(ForumComment forumComment, Long forumPostId, Long studentId) {
        ForumPost forumPost = em.find(ForumPost.class, forumPostId);
        Student student = em.find(Student.class, studentId);
        student.getComments().add(forumComment);
        forumComment.setStudent(student);
        forumComment.setStudentId(studentId);
        forumComment.setStudentFirstName(student.getFirstName());
        forumComment.setStudentLastName(student.getLastName());
        forumPost.getForumComments().add(forumComment);
        forumComment.setForumPost(forumPost);
        //forumComment.setTimeOfCreation(LocalDateTime.now());
        //forumComment.setLikedStudents(new ArrayList());
        //forumComment.setDislikedStudents(new ArrayList());
        //forumComment.setReplies(new ArrayList());
        em.persist(forumComment);
        em.flush();
        
        return forumComment;
    }
    
    @Override
    public ForumComment createNewForumReply(ForumComment forumReply, Long forumCommentId, Long forumPostId, Long studentId) {
        ForumComment forumComment = em.find(ForumComment.class, forumCommentId);
        ForumComment reply = createNewForumComment(forumReply, forumPostId, studentId);
        forumComment.getReplies().add(reply);
        
        return reply;
    }
    
    @Override
    public void updateShowReply(Long forumCommentId) {
        ForumComment forumComment = em.find(ForumComment.class, forumCommentId);
        forumComment.setShowReplies(!forumComment.getShowReplies());
    }

    @Override
    public void updateForumComment(ForumComment forumComment) {
        
        ForumComment oldComment = retrieveForumCommentById(forumComment.getCommentId());

        oldComment.setMessage(forumComment.getMessage());
        oldComment.setIsEdited(forumComment.getIsEdited());
        oldComment.setLastEdit(forumComment.getLastEdit());
        oldComment.setNoOfLikes(forumComment.getNoOfLikes());
        oldComment.setNoOfDislikes(forumComment.getNoOfDislikes());
        
        System.out.println("is inappropriate " + forumComment.getIsInappropriate());
        
        oldComment.setIsInappropriate(forumComment.getIsInappropriate());
        oldComment.setTimeOfCreation(forumComment.getTimeOfCreation());
        oldComment.setForumPost(forumComment.getForumPost());
        oldComment.setStudent(forumComment.getStudent());
        oldComment.setStudentId(forumComment.getStudentId());
        oldComment.setStudentFirstName(forumComment.getStudentFirstName());
        oldComment.setStudentLastName(forumComment.getStudentLastName());
        oldComment.setLikedStudents(forumComment.getLikedStudents());
        oldComment.setDislikedStudents(forumComment.getDislikedStudents());
        oldComment.setReplies(forumComment.getReplies());
        oldComment.setIsAReply(forumComment.getIsAReply());
 
    }
    
    @Override
    public void editForumComment(ForumComment forumComment) {
        ForumComment oldComment = retrieveForumCommentById(forumComment.getCommentId());

        oldComment.setMessage(forumComment.getMessage());
        oldComment.setIsEdited(true);
        oldComment.setLastEdit(LocalDateTime.now());
        oldComment.setNoOfLikes(forumComment.getNoOfLikes());
        oldComment.setNoOfDislikes(forumComment.getNoOfDislikes());
        oldComment.setIsInappropriate(forumComment.getIsInappropriate());
        oldComment.setTimeOfCreation(forumComment.getTimeOfCreation());
        oldComment.setForumPost(forumComment.getForumPost());
        oldComment.setStudent(forumComment.getStudent());
        oldComment.setStudentId(forumComment.getStudentId());
        oldComment.setStudentFirstName(forumComment.getStudentFirstName());
        oldComment.setStudentLastName(forumComment.getStudentLastName());
        oldComment.setLikedStudents(forumComment.getLikedStudents());
        oldComment.setDislikedStudents(forumComment.getDislikedStudents());
        oldComment.setReplies(forumComment.getReplies());
        oldComment.setIsAReply(forumComment.getIsAReply());
    }
    
    @Override
    public void editForumCommentByAdmin(ForumComment forumComment) {
        ForumComment oldComment = retrieveForumCommentById(forumComment.getCommentId());

        oldComment.setMessage(forumComment.getMessage());
        oldComment.setNoOfLikes(forumComment.getNoOfLikes());
        oldComment.setNoOfDislikes(forumComment.getNoOfDislikes());
        oldComment.setIsInappropriate(forumComment.getIsInappropriate());
        oldComment.setTimeOfCreation(forumComment.getTimeOfCreation());
        oldComment.setForumPost(forumComment.getForumPost());
        oldComment.setStudent(forumComment.getStudent());
        oldComment.setStudentId(forumComment.getStudentId());
        oldComment.setStudentFirstName(forumComment.getStudentFirstName());
        oldComment.setStudentLastName(forumComment.getStudentLastName());
        oldComment.setLikedStudents(forumComment.getLikedStudents());
        oldComment.setDislikedStudents(forumComment.getDislikedStudents());
        oldComment.setReplies(forumComment.getReplies());
    }

    @Override
    public void deleteForumComment(Long forumCommentId) {
        ForumComment forumComment = em.find(ForumComment.class, forumCommentId);
        Student student = em.find(Student.class, forumComment.getStudent().getStudentId());
        student.getComments().remove(forumComment);
        ForumPost forumPost = forumComment.getForumPost();
        forumPost.getForumComments().remove(forumComment);
        forumPostSessionBeanLocal.updateForumPost(forumPost);
        
        List<ForumComment> forumReplies = new ArrayList<>(forumComment.getReplies());

        synchronized (forumReplies) {
            Iterator<ForumComment> iterator = forumReplies.iterator();
            while (iterator.hasNext()) {
                ForumComment forumReply = iterator.next();
                forumComment.getReplies().remove(forumReply);
                deleteForumComment(forumReply.getCommentId());
                iterator.remove();
            }
        }

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
    public void likeForumComment(Long forumCommentId, Long studentId) {
        ForumComment forumComment = em.find(ForumComment.class, forumCommentId);
        
        forumComment.setNoOfLikes(forumComment.getNoOfLikes() + 1);
        
        forumComment.getLikedStudents().add(studentId);
        if (forumComment.getDislikedStudents().contains(studentId)) {
            undislikeForumComment(forumCommentId, studentId);
        }
    }

    @Override
    public void unlikeForumComment(Long forumCommentId, Long studentId) {
        ForumComment forumComment = em.find(ForumComment.class, forumCommentId);
        
        forumComment.setNoOfLikes(forumComment.getNoOfLikes() - 1);
        forumComment.getLikedStudents().remove(studentId);
    }

    @Override
    public void dislikeForumComment(Long forumCommentId, Long studentId) {
        ForumComment forumComment = em.find(ForumComment.class, forumCommentId);
        
        forumComment.setNoOfDislikes(forumComment.getNoOfDislikes() + 1);
        forumComment.getDislikedStudents().add(studentId);
        if (forumComment.getLikedStudents().contains(studentId)) {
            unlikeForumComment(forumCommentId, studentId);
        }
    }

    @Override
    public void undislikeForumComment(Long forumCommentId, Long studentId) {
        ForumComment forumComment = em.find(ForumComment.class, forumCommentId);
        
        forumComment.setNoOfDislikes(forumComment.getNoOfDislikes() - 1);
        forumComment.getDislikedStudents().remove(studentId);
    }
    
    @Override
    public void replyForumComment(ForumComment reply, Long replyingToCommentId, Long studentId) {
        ForumComment forumComment = em.find(ForumComment.class, replyingToCommentId);
        reply.setIsAReply(true);
        ForumComment theReply = createNewForumComment(reply, forumComment.getForumPost().getPostId(), studentId);
        
        forumComment.getReplies().add(theReply);
    }

}
