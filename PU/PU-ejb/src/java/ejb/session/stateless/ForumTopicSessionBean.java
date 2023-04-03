/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

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
public class ForumTopicSessionBean implements ForumTopicSessionBeanLocal {

    @EJB(name = "ForumPostSessionBeanLocal")
    private ForumPostSessionBeanLocal forumPostSessionBeanLocal;

    @PersistenceContext(unitName = "PU-ejbPU")
    private EntityManager em;   

    @Override
    public void createNewForumTopic(ForumTopic forumTopic, Long studentId) {
        Student student = em.find(Student.class, studentId);
        //forumTopic.setTimeOfCreation(LocalDateTime.now());
        student.getTopics().add(forumTopic);
        forumTopic.setStudent(student);
        forumTopic.setStudentId(studentId);
        forumTopic.setStudentFirstName(student.getFirstName());
        forumTopic.setStudentLastName(student.getLastName());
        //forumTopic.setForumPosts(new ArrayList());
        em.persist(forumTopic);
        em.flush();
    }

    @Override
    public void updateForumTopic(ForumTopic forumTopic) {     
        
        ForumTopic oldTopic = retrieveForumTopicById(forumTopic.getTopicId());

        oldTopic.setTopicName(forumTopic.getTopicName());
        oldTopic.setIsEdited(forumTopic.getIsEdited());
        oldTopic.setLastEdit(forumTopic.getLastEdit());
        oldTopic.setIsInappropriate(forumTopic.getIsInappropriate());
        oldTopic.setTimeOfCreation(forumTopic.getTimeOfCreation());
        oldTopic.setForumPosts(forumTopic.getForumPosts());
        oldTopic.setStudent(forumTopic.getStudent());
        oldTopic.setStudentId(forumTopic.getStudentId());
        oldTopic.setStudentFirstName(forumTopic.getStudentFirstName());
        oldTopic.setStudentLastName(forumTopic.getStudentLastName());
        
    }
    
    @Override
    public void editForumTopic(ForumTopic forumTopic) {
        ForumTopic oldTopic = retrieveForumTopicById(forumTopic.getTopicId());

        oldTopic.setTopicName(forumTopic.getTopicName());
        oldTopic.setIsEdited(true);
        oldTopic.setLastEdit(LocalDateTime.now());
        oldTopic.setIsInappropriate(forumTopic.getIsInappropriate());
        oldTopic.setTimeOfCreation(forumTopic.getTimeOfCreation());
        oldTopic.setForumPosts(forumTopic.getForumPosts());
        oldTopic.setStudent(forumTopic.getStudent());
        oldTopic.setStudentId(forumTopic.getStudentId());
        oldTopic.setStudentFirstName(forumTopic.getStudentFirstName());
        oldTopic.setStudentLastName(forumTopic.getStudentLastName());
    }

    @Override
    public void deleteForumTopic(Long forumTopicId) {
        ForumTopic forumTopic = em.find(ForumTopic.class, forumTopicId);
        Student student = em.find(Student.class, forumTopic.getStudent().getStudentId());
        student.getTopics().remove(forumTopic);
        List<ForumPost> forumPosts = forumTopic.getForumPosts();

        synchronized (forumPosts) {
            List<ForumPost> copyOfForumPosts = new ArrayList<>(forumPosts); // create a copy of the list
            for (ForumPost forumPost : copyOfForumPosts) {
                forumPostSessionBeanLocal.deleteForumPost(forumPost.getPostId());
                forumPosts.remove(forumPost); // remove the forumPost from the original list
            }
        }

        em.remove(forumTopic);
        em.flush();
    }

    @Override
    public List<ForumTopic> retrieveAllForumTopics() {
        Query query = em.createQuery("SELECT t FROM ForumTopic t");
        return query.getResultList();
    }

    @Override
    public ForumTopic retrieveForumTopicById(Long forumTopicId) {
        ForumTopic forumTopic = em.find(ForumTopic.class, forumTopicId);
        return forumTopic;
    }
    
    @Override
    public List<ForumTopic> retrieveForumTopicsByStudentId(Long studentId) {
        Student student = em.find(Student.class, studentId);
        return student.getTopics();
    }

    @Override
    public List<ForumTopic> searchForumTopics(String topicName) {
        Query q;
        if (topicName != null) {
            q = em.createQuery("SELECT ft FROM ForumTopic ft WHERE "
                    + "LOWER(ft.topicName) LIKE :name");
            q.setParameter("name", "%" + topicName.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT ft FROM ForumTopic ft");
        }
        return q.getResultList();
    }
    
    @Override
    public List<ForumTopic> searchForumTopicsByStudent(String topicName, Long studentId) {
        Query q;
        if (topicName != null) {
            q = em.createQuery("SELECT ft FROM ForumTopic ft WHERE "
                    + "LOWER(ft.topicName) LIKE :topicName");
            q.setParameter("topicName", "%" + topicName.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT ft FROM ForumTopic ft");
        }
        
        List<ForumTopic> forumTopics = q.getResultList();
        List<ForumTopic> searchTopics = forumTopics;
        
        for(ForumTopic forumTopic : forumTopics) {
            if (forumTopic.getStudentId() != studentId) {
                searchTopics.remove(forumTopic);
            }
        }
        
        return searchTopics;
    }
    
    @Override
    public void reportForumTopic(Long forumTopicId) {
        ForumTopic forumTopic = em.find(ForumTopic.class, forumTopicId);
        
        forumTopic.setIsInappropriate(true);
    }
}