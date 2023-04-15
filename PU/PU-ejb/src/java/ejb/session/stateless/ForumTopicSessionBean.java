/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ForumPost;
import entity.ForumTopic;
import entity.PU;
import entity.NUSchangeAdmin;
import entity.Student;
import error.NoResultException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public void createNewForumTopic(ForumTopic forumTopic, Long studentId, Long puId) {
        Student student = em.find(Student.class, studentId);
        PU pu = em.find(PU.class, puId);
        student.getTopics().add(forumTopic);
        forumTopic.setStudent(student);
        forumTopic.setStudentId(studentId);
        forumTopic.setStudentFirstName(student.getFirstName());
        forumTopic.setStudentLastName(student.getLastName());
        forumTopic.setPu(pu);
        forumTopic.setPuName(pu.getName());
        em.persist(forumTopic);
        em.flush();
    }

    @Override
    public void createNewForumTopicByAdmin(ForumTopic forumTopic, Long adminId, Long puId) throws NoResultException {
        NUSchangeAdmin nusChangeAdmin = em.find(NUSchangeAdmin.class, adminId);
        PU pu = em.find(PU.class, puId);
        nusChangeAdmin.getForumTopics().add(forumTopic);
        forumTopic.setAdmin(nusChangeAdmin);
        forumTopic.setPu(pu);
        forumTopic.setPuName(pu.getName());
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
        oldTopic.setPu(forumTopic.getPu());

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
        oldTopic.setPu(forumTopic.getPu());
    }

    @Override
    public void editForumTopicByAdmin(ForumTopic forumTopic) {
        ForumTopic oldTopic = retrieveForumTopicById(forumTopic.getTopicId());

        oldTopic.setLastEdit(forumTopic.getLastEdit());
        oldTopic.setTopicName(forumTopic.getTopicName());
        oldTopic.setIsInappropriate(forumTopic.getIsInappropriate());
        oldTopic.setTimeOfCreation(forumTopic.getTimeOfCreation());
        oldTopic.setForumPosts(forumTopic.getForumPosts());
        oldTopic.setStudent(forumTopic.getStudent());
        oldTopic.setStudentId(forumTopic.getStudentId());
        oldTopic.setStudentFirstName(forumTopic.getStudentFirstName());
        oldTopic.setStudentLastName(forumTopic.getStudentLastName());
        oldTopic.setPu(forumTopic.getPu());
    }

    @Override
    public void deleteForumTopic(Long forumTopicId) {
        ForumTopic forumTopic = em.find(ForumTopic.class, forumTopicId);

        if (forumTopic.getStudent() != null) {
            Student student = em.find(Student.class, forumTopic.getStudentId());
            student.getTopics().remove(forumTopic);
        } else {
            NUSchangeAdmin admin = em.find(NUSchangeAdmin.class, forumTopic.getAdmin().getAdminId());
            admin.getForumTopics().remove(forumTopic);
        }

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
    public List<ForumTopic> retrievePUForumTopicsByStudentId(Long puId, Long studentId) {

        List<ForumTopic> studentTopics = retrieveForumTopicsByStudentId(studentId);

        List<ForumTopic> puStudentTopics = new ArrayList<>();

        for (ForumTopic forumTopic : studentTopics) {
            if (forumTopic.getPu().getPuId() == puId) {
                puStudentTopics.add(forumTopic);
            }
        }
        return puStudentTopics;
    }

    @Override
    public List<ForumTopic> retrieveForumTopicsByPuId(Long puId) {
        Query query = em.createQuery("SELECT t FROM ForumTopic t WHERE t.pu.puId = :puId");
        query.setParameter("puId", puId);
        List<ForumTopic> forumTopics = query.getResultList();

        return forumTopics;
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
        List<ForumTopic> searchTopics = new ArrayList();
        
        for (ForumTopic forumTopic : forumTopics) {
            if (forumTopic.getStudentId() == studentId) {
                searchTopics.add(forumTopic);
            }
        }

        return searchTopics;
    }

    @Override
    public List<ForumTopic> searchForumTopicsByStudentAndPu(String topicName, Long studentId, Long puId) {

        List<ForumTopic> puForumTopics = searchForumTopicsByPu(topicName, puId);
        
        List<ForumTopic> studentPuForumTopics = new ArrayList();
        
        for (ForumTopic forumTopic : puForumTopics) {
            if (forumTopic.getStudentId() == studentId) {
                studentPuForumTopics.add(forumTopic);
            }
        }

        return studentPuForumTopics;
    }

    @Override
    public List<ForumTopic> searchForumTopicsByPu(String topicName, Long puId) {
        Query q;
        if (topicName != null) {
            q = em.createQuery("SELECT ft FROM ForumTopic ft WHERE "
                    + "LOWER(ft.topicName) LIKE :topicName");
            q.setParameter("topicName", "%" + topicName.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT ft FROM ForumTopic ft");
        }

        List<ForumTopic> forumTopics = q.getResultList();
        List<ForumTopic> searchTopics = new ArrayList<>();

        for (ForumTopic forumTopic : forumTopics) {
            if (forumTopic.getPu().getPuId() == puId) {
                searchTopics.add(forumTopic);
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
