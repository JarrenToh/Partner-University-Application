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
        forumTopic.setTimeOfCreation(LocalDateTime.now());
        student.getTopics().add(forumTopic);
        forumTopic.setStudent(student);
        em.persist(forumTopic);
        em.flush();
    }

    @Override
    public void updateForumTopic(ForumTopic forumTopic) {     
        
        ForumTopic oldTopic = retrieveForumTopicById(forumTopic.getTopicId());

        oldTopic.setTopicName(forumTopic.getTopicName());
        oldTopic.setIsInappropriate(forumTopic.getIsInappropriate());
        
    }

    @Override
    public void deleteForumTopic(Long forumTopicId) {
        ForumTopic forumTopic = em.find(ForumTopic.class, forumTopicId);
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
}