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
        forumComment.setTimeOfCreation(LocalDateTime.now());
        forumComment.setLikedStudents(new ArrayList());
        forumComment.setDislikedStudents(new ArrayList());
        forumComment.setReplies(new ArrayList());
        em.persist(forumComment);
        em.flush();
        
        return forumComment;
    }
    
    @Override
    public ForumComment createNewForumReply(ForumComment forumReply, Long forumCommentId, Long forumPostId, Long studentId) {
        ForumComment forumComment = em.find(ForumComment.class, forumCommentId);
        forumReply.setParentComment(forumComment);
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
        oldComment.setParentComment(forumComment.getParentComment());
 
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
        oldComment.setParentComment(forumComment.getParentComment());
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
        // remove all replies
//        System.out.println("The number of replies:" + forumComment.getReplies().size());
//        List<ForumComment> forumReplies = new ArrayList<>(forumComment.getReplies());
//        System.out.println("Hi");
//        for (ForumComment reply : forumReplies) {
//            deleteForumComment(reply.getCommentId());
//            System.out.println("deleted");
//        }
//        // remove forum comment from post and student
//        Student student = em.find(Student.class, forumComment.getStudentId());
//        System.out.println("find student " + student);
//        student.getComments().remove(forumComment);
//        System.out.println("remove comment from student");
        ForumPost forumPost = em.find(ForumPost.class,forumComment.getForumPost().getPostId());
//        System.out.println("find forumPost");
        forumPost.getForumComments().remove(forumComment);
//        System.out.println("remove comment from post");
//        forumPostSessionBeanLocal.updateForumPost(forumPost);
        if (forumComment.getParentComment() != null) {
            ForumComment parentComment = forumComment.getParentComment();
            System.out.println("find parent comment");
            parentComment.getReplies().remove(forumComment);
            System.out.println("remove reply from parent comment");
            updateForumComment(parentComment);
        }
        // remove comment
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
        forumComment.setReplies(new ArrayList());
        reply.setParentComment(forumComment);
        ForumComment theReply = createNewForumComment(reply, forumComment.getForumPost().getPostId(), studentId);
        
        forumComment.getReplies().add(theReply);
    }
    
       
    @Override
    public List<ForumComment> searchForumComment(String searchQuery, Long postId) {
        Query q;
        if (searchQuery != null) {
            q = em.createQuery("SELECT fc FROM ForumComment fc WHERE "
                    + "LOWER(fc.message) LIKE :message");
            q.setParameter("message", "%" + searchQuery.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT fc FROM ForumPost fc");
        }
        List<ForumComment> comments = q.getResultList();
        List<ForumComment> searches = new ArrayList();
        
        for (ForumComment forumComment : comments) {
            if (forumComment.getForumPost().getPostId() == postId) {
                searches.add(forumComment);
            }
        }
        
        return searches;
    }


}
