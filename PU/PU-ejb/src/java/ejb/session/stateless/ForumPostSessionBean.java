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
        forumPost.setTimeOfCreation(LocalDateTime.now());
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
        oldPost.setNoOfLikes(forumPost.getNoOfLikes());
        oldPost.setNoOfDislikes(forumPost.getNoOfDislikes());
        oldPost.setIsInappropriate(forumPost.getIsInappropriate());
    }

    @Override
    public synchronized void deleteForumPost(Long forumPostId) {
        ForumPost forumPost = em.find(ForumPost.class, forumPostId);
        ForumTopic forumTopic = forumPost.getForumTopic();
        forumTopic.getForumPosts().remove(forumPost);
        forumTopicSessionBeanLocal.updateForumTopic(forumTopic);
        List<ForumComment> forumComments = new ArrayList<>(forumPost.getForumComments());

        synchronized (forumComments) {
            Iterator<ForumComment> iterator = forumComments.iterator();
            while (iterator.hasNext()) {
                ForumComment forumComment = iterator.next();
                forumCommentSessionBeanLocal.deleteForumComment(forumComment.getCommentId());
                iterator.remove();
            }
        }

        em.remove(forumPost);
        em.flush();
    }

    @Override
    public List<ForumPost> retrieveAllForumPosts() {
        Query query = em.createQuery("SELECT p FROM ForumPost p");
        return query.getResultList();
    }

    @Override
    public ForumPost retrieveForumPostById(Long forumPostId) {
        ForumPost forumPost = em.find(ForumPost.class, forumPostId);
        return forumPost;
    }

    @Override
    public List<ForumPost> retrieveAppropriateForumPosts() {
        Query query = em.createQuery("SELECT f FROM ForumPost WHERE f.isInappropriate = :appropriate");
        query.setParameter("appropriate", true);
        
        return query.getResultList();
    }

    @Override
    public List<ForumPost> retrieveInappropriateForumPosts() {
        Query query = em.createQuery("SELECT f FROM ForumPost WHERE f.isInappropriate = :appropriate");
        query.setParameter("appropriate", false);
        
        return query.getResultList();
        
    }

    @Override
    public void likeForumPost(Long forumPostId) {
        ForumPost forumPost = em.find(ForumPost.class, forumPostId);
        
        forumPost.setNoOfLikes(forumPost.getNoOfLikes() + 1);
    }

    @Override
    public void unlikeForumPost(Long forumPostId) {
        ForumPost forumPost = em.find(ForumPost.class, forumPostId);
        
        forumPost.setNoOfLikes(forumPost.getNoOfLikes() - 1);
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
    public void dislikeForumPost(Long forumPostId) {
        ForumPost forumPost = em.find(ForumPost.class, forumPostId);
        
        forumPost.setNoOfDislikes(forumPost.getNoOfDislikes() + 1);
    }

    @Override
    public void undislikeForumPost(Long forumPostId) {
        ForumPost forumPost = em.find(ForumPost.class, forumPostId);
        
        forumPost.setNoOfDislikes(forumPost.getNoOfDislikes() - 1);
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
        List<ForumPost> searchPosts = forumPosts;
        
        for(ForumPost forumPost : forumPosts) {
            if (forumPost.getForumTopic().getTopicId() != topicId) {
                searchPosts.remove(forumPost);
            }
        }
        
        return searchPosts;
    }

    @Override
    public List<ForumPost> retrieveForumPostByTopic(Long topicId) {
        
        List<ForumPost> forumPosts = retrieveAllForumPosts();
        
        List<ForumPost> targetList = forumPosts;
        
        for (ForumPost forumPost : forumPosts) {
            if (forumPost.getForumTopic().getTopicId() != topicId) {
                targetList.remove(forumPost);
            }
        }
        
        return targetList;
    }
    
}
