/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ForumComment;
import entity.ForumPost;
import entity.ForumTopic;
import entity.Student;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
public class ForumPostSessionBean implements ForumPostSessionBeanLocal {

    @EJB(name = "ForumCommentSessionBeanLocal")
    private ForumCommentSessionBeanLocal forumCommentSessionBeanLocal;

    @EJB(name = "ForumTopicSessionBeanLocal")
    private ForumTopicSessionBeanLocal forumTopicSessionBeanLocal;

    @PersistenceContext(unitName = "PU-ejbPU")
    private EntityManager em;
    
    @Override
    public void createNewForumPost(ForumPost forumPost, Long forumTopicId, Long studentId) {
        ForumTopic forumTopic = em.find(ForumTopic.class, forumTopicId);
        Student student = em.find(Student.class, studentId);
        forumTopic.getForumPosts().add(forumPost);
        student.getPosts().add(forumPost);
        forumPost.setForumTopic(forumTopic);
        forumPost.setStudent(student);
        forumPost.setStudentId(studentId);
        forumPost.setStudentFirstName(student.getFirstName());
        forumPost.setStudentLastName(student.getLastName());
        forumPost.setTimeOfCreation(LocalDateTime.now());
        forumPost.setLikedStudents(new ArrayList());
        forumPost.setDislikedStudents(new ArrayList());
        forumPost.setForumComments(new ArrayList());
        em.persist(forumPost);
        em.flush();
    }

    @Override
    public void updateForumPost(ForumPost forumPost) {
        //em.merge(forumPost);
        //em.flush();
        
        ForumPost oldPost = retrieveForumPostById(forumPost.getPostId());

        oldPost.setTitle(forumPost.getTitle());
        oldPost.setMessage(forumPost.getMessage());
        oldPost.setIsEdited(forumPost.getIsEdited());
        oldPost.setLastEdit(forumPost.getLastEdit());
        oldPost.setNoOfLikes(forumPost.getNoOfLikes());
        oldPost.setNoOfDislikes(forumPost.getNoOfDislikes());
        oldPost.setIsInappropriate(forumPost.getIsInappropriate());
        oldPost.setTimeOfCreation(forumPost.getTimeOfCreation());
        oldPost.setForumTopic(forumPost.getForumTopic());
        oldPost.setStudent(forumPost.getStudent());
        oldPost.setStudentId(forumPost.getStudentId());
        oldPost.setStudentFirstName(forumPost.getStudentFirstName());
        oldPost.setStudentLastName(forumPost.getStudentLastName());
        oldPost.setForumComments(forumPost.getForumComments());
        oldPost.setLikedStudents(forumPost.getLikedStudents());
        oldPost.setDislikedStudents(forumPost.getDislikedStudents());
    }
    
    @Override
    public void editForumPost(ForumPost forumPost) {
        ForumPost oldPost = retrieveForumPostById(forumPost.getPostId());

        oldPost.setTitle(forumPost.getTitle());
        oldPost.setMessage(forumPost.getMessage());
        oldPost.setIsEdited(true);
        oldPost.setLastEdit(LocalDateTime.now());
//        oldPost.setNoOfLikes(forumPost.getNoOfLikes());
//        oldPost.setNoOfDislikes(forumPost.getNoOfDislikes());
//        oldPost.setIsInappropriate(forumPost.getIsInappropriate());
//        oldPost.setTimeOfCreation(forumPost.getTimeOfCreation());
//        oldPost.setForumTopic(forumPost.getForumTopic());
//        oldPost.setStudent(forumPost.getStudent());
//        oldPost.setStudentId(forumPost.getStudentId());
//        oldPost.setStudentFirstName(forumPost.getStudentFirstName());
//        oldPost.setStudentLastName(forumPost.getStudentLastName());
//        oldPost.setForumComments(forumPost.getForumComments());
//        oldPost.setLikedStudents(forumPost.getLikedStudents());
//        oldPost.setDislikedStudents(forumPost.getDislikedStudents());
    }
    
    @Override
    public void editForumPostByAdmin(ForumPost forumPost) {
        ForumPost oldPost = retrieveForumPostById(forumPost.getPostId());

        oldPost.setTitle(forumPost.getTitle());
        oldPost.setMessage(forumPost.getMessage());
        oldPost.setNoOfLikes(forumPost.getNoOfLikes());
        oldPost.setNoOfDislikes(forumPost.getNoOfDislikes());
        oldPost.setIsInappropriate(forumPost.getIsInappropriate());
        oldPost.setTimeOfCreation(forumPost.getTimeOfCreation());
        oldPost.setForumTopic(forumPost.getForumTopic());
        oldPost.setStudent(forumPost.getStudent());
        oldPost.setStudentId(forumPost.getStudentId());
        oldPost.setStudentFirstName(forumPost.getStudentFirstName());
        oldPost.setStudentLastName(forumPost.getStudentLastName());
        oldPost.setForumComments(forumPost.getForumComments());
        oldPost.setLikedStudents(forumPost.getLikedStudents());
        oldPost.setDislikedStudents(forumPost.getDislikedStudents());
    }

    @Override
    public synchronized void deleteForumPost(Long forumPostId) {
        ForumPost forumPost = em.find(ForumPost.class, forumPostId);
        ForumTopic forumTopic = em.find(ForumTopic.class,forumPost.getForumTopic().getTopicId());
        forumTopic.getForumPosts().remove(forumPost);
//        forumTopicSessionBeanLocal.updateForumTopic(forumTopic);
        em.remove(forumPost);
        em.flush();
    }

    @Override
    public List<ForumPost> retrieveAllForumPosts() {
        //Query query = em.createQuery("SELECT fp FROM ForumPost fp");
        Query query = em.createQuery("SELECT fp FROM ForumPost fp JOIN FETCH fp.student");
        return query.getResultList();
    }

    @Override
    public ForumPost retrieveForumPostById(Long forumPostId) {
        ForumPost forumPost = em.find(ForumPost.class, forumPostId);
        return forumPost;
    }

    @Override
    public List<ForumPost> retrieveAppropriateForumPosts() {
        Query query = em.createQuery("SELECT f FROM ForumPost");
        query.setParameter("appropriate", true);
        
        return query.getResultList();
    }

    @Override
    public List<ForumPost> retrieveInappropriateForumPosts() {
        Query query = em.createQuery("SELECT f FROM ForumPost fp WHERE f.isInappropriate = :appropriate");
        query.setParameter("appropriate", false);
        
        return query.getResultList();
        
    }

    @Override
    public void likeForumPost(Long forumPostId, Long studentId) {
        ForumPost forumPost = em.find(ForumPost.class, forumPostId);
        
        forumPost.setNoOfLikes(forumPost.getNoOfLikes() + 1);
        forumPost.getLikedStudents().add(studentId);
        if (forumPost.getDislikedStudents().contains(studentId)) {
            undislikeForumPost(forumPostId, studentId);
        }
    }

    @Override
    public void unlikeForumPost(Long forumPostId, Long studentId) {
        ForumPost forumPost = em.find(ForumPost.class, forumPostId);
        
        forumPost.setNoOfLikes(forumPost.getNoOfLikes() - 1);
        forumPost.getLikedStudents().remove(studentId);
    }

    @Override
    public void reportForumPost(Long forumPostId) {
        ForumPost forumPost = em.find(ForumPost.class, forumPostId);
        
        forumPost.setIsInappropriate(true);
    }
    
    public void resolveForumPost(Long forumPostId) {
        ForumPost forumPost = em.find(ForumPost.class, forumPostId);
        
        forumPost.setIsInappropriate(false);
    }

    @Override
    public void dislikeForumPost(Long forumPostId, Long studentId) {
        ForumPost forumPost = em.find(ForumPost.class, forumPostId);
        
        forumPost.setNoOfDislikes(forumPost.getNoOfDislikes() + 1);
        forumPost.getDislikedStudents().add(studentId);
        if (forumPost.getLikedStudents().contains(studentId)) {
            unlikeForumPost(forumPostId, studentId);
        }
    }

    @Override
    public void undislikeForumPost(Long forumPostId, Long studentId) {
        ForumPost forumPost = em.find(ForumPost.class, forumPostId);
        
        forumPost.setNoOfDislikes(forumPost.getNoOfDislikes() - 1);
        forumPost.getDislikedStudents().remove(studentId);
    }
    
    @Override
    public List<ForumPost> searchForumPost(String postTitle) {
        Query q;
        if (postTitle != null) {
            q = em.createQuery("SELECT fp FROM ForumPost fp WHERE "
                    + "LOWER(fp.title) LIKE :title");
            q.setParameter("title", "%" + postTitle.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT fp FROM ForumPost fp");
        }
        return q.getResultList();
    }

    @Override
    public List<ForumPost> searchForumPostByTopic(String postTitle, Long topicId) {
        Query q;
        if (postTitle != null) {
            q = em.createQuery("SELECT fp FROM ForumPost fp WHERE "
                    + "LOWER(fp.title) LIKE :title");
            q.setParameter("title", "%" + postTitle.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT fp FROM ForumPost fp");
        }
        
        List<ForumPost> forumPosts = q.getResultList();
        List<ForumPost> searchPosts = new ArrayList();
        
        for(ForumPost forumPost : forumPosts) {
            if (forumPost.getForumTopic().getTopicId() == topicId) {
                searchPosts.add(forumPost);
            }
        }
        
        return searchPosts;
    }
    
    @Override
    public List<ForumPost> searchForumPostsByTopicAndStudent(String postTitle, Long topicId, Long studentId) {
       
        List<ForumPost> forumPosts = searchForumPostByTopic(postTitle, topicId);
        List<ForumPost> searchPosts = new ArrayList();
        
        for (ForumPost forumPost : forumPosts) {
            if (forumPost.getStudentId() == studentId) {
                searchPosts.add(forumPost);
            }
        }
        
        return searchPosts;
    }

    @Override
    public List<ForumPost> retrieveForumPostByTopic(Long topicId) {

        List<ForumPost> forumPosts = retrieveAllForumPosts();

        List<ForumPost> targetList = new ArrayList<>(forumPosts); // create a copy of the list to avoid modifying the original

        Iterator<ForumPost> iter = targetList.iterator();
        while (iter.hasNext()) {
            ForumPost forumPost = iter.next();
            if (forumPost.getForumTopic().getTopicId() != topicId) {
                iter.remove(); // remove using the iterator
            }
        }

        return targetList;
    }    
        
    @Override
    public List<ForumPost> retrieveForumPostsByTopicAndStudent(Long topicId, Long studentId) {
        
        List<ForumPost> forumPosts = retrieveForumPostByTopic(topicId);

        List<ForumPost> targetList = new ArrayList<>(forumPosts); // create a copy of the list to avoid modifying the original

        Iterator<ForumPost> iter = targetList.iterator();
        while (iter.hasNext()) {
            ForumPost forumPost = iter.next();
            if (forumPost.getStudentId() != studentId) {
                iter.remove(); // remove using the iterator
            }
        }

        return targetList;
        
    }
}
