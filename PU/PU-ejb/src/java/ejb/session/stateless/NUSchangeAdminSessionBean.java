/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Enquiry;
import entity.FAQ;
import entity.ForumTopic;
import entity.NUSchangeAdmin;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.UserGroupEnum;

/**
 *
 * @author wjahoward
 */
@Stateless
public class NUSchangeAdminSessionBean implements NUSchangeAdminSessionBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    private EntityManager em;

    @Override
    public NUSchangeAdmin initNewNUSchangeAdmin(NUSchangeAdmin nuschangeAdmin) {
        em.persist(nuschangeAdmin);
        em.flush();

        return nuschangeAdmin;
    }

    @Override
    public List<FAQ> retrieveAllFAQsByUserSupportAdmin(Long adminId) {
        NUSchangeAdmin admin = retrieveAdmin(adminId);
        
        if (admin.getUserGroupEnum() == UserGroupEnum.SYSTEM_SUPPORT) {
            return new ArrayList<>();
        }
       
        Query q = em.createQuery("SELECT f FROM FAQ f WHERE f.createdBy = :admin");
        q.setParameter("admin", admin);
        List<FAQ> faqs = q.getResultList();

        return faqs;
    }

    @Override
    public List<Enquiry> retrieveAllEnquiresByUserSupportAdmin(Long adminId) {
        NUSchangeAdmin admin = retrieveAdmin(adminId);

        if (admin.getUserGroupEnum() == UserGroupEnum.SYSTEM_SUPPORT) {
            return new ArrayList<>();
        }

        Query q = em.createQuery("SELECT e FROM Enquiry e WHERE e.nuschangeAdmin.adminId = :admin");
        q.setParameter("admin", admin);
        List<Enquiry> enquiries = q.getResultList();
        
        return enquiries;
    }

    // TODO
    @Override
    public List<ForumTopic> retrieveAllForumTopicsBySystemSupportAdmin(Long adminId) {
        NUSchangeAdmin admin = retrieveAdmin(adminId);

        if (admin.getUserGroupEnum() == UserGroupEnum.USER_SUPPORT) {
            return new ArrayList<>();
        }

        return admin.getForumTopics();
    }

    @Override
    public NUSchangeAdmin login(String username, String password) {
        Query query = em.createQuery("SELECT n FROM NUSchangeAdmin n WHERE n.username = :username AND n.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);

        return (NUSchangeAdmin) query.getSingleResult();
    }

    private NUSchangeAdmin retrieveAdmin(Long adminId) {
        Query query = em.createQuery("SELECT n FROM NUSchangeAdmin n WHERE n.adminId = :adminId");
        query.setParameter("adminId", adminId);

        NUSchangeAdmin admin = (NUSchangeAdmin) query.getSingleResult();
        return admin;
    }

}
