/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FAQ;
import entity.NUSchangeAdmin;
import error.NoResultException;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wjahoward
 */
@Stateless
public class FAQSessionBean implements FAQSessionBeanLocal {

    @PersistenceContext(unitName = "PU-ejbPU")
    private EntityManager em;

    @Override
    public FAQ createFAQ(FAQ faq, Long adminId) throws NoResultException {        
        Query q = em.createQuery("SELECT a FROM NUSchangeAdmin a WHERE a.adminId = :adminId");
        q.setParameter("adminId", adminId);

        NUSchangeAdmin admin = (NUSchangeAdmin)q.getSingleResult();
        
        faq.setCreated(LocalDateTime.now());
        faq.setCreatedBy(admin);
        
        em.persist(faq);
        em.flush();
        
        return faq;
    }

    @Override
    public FAQ retrieveFAQ(Long faqId) {
        return getFAQ(faqId);
    }

    private FAQ getFAQ(Long faqId) {
        Query q = em.createQuery("SELECT f FROM FAQ f WHERE f.faqId = :faqId");
        q.setParameter("faqId", faqId);

        return (FAQ)q.getSingleResult();
    }

    @Override
    public List<FAQ> retrieveAllFAQs() {
        Query query = em.createQuery("SELECT f FROM FAQ f");
        return query.getResultList();
    }

    @Override
    public void updateFAQ(Long faqId, Long adminId, String question, String answer) {
        FAQ faq = retrieveSpecificFAQ(faqId, adminId);

        faq.setQuestion(question);
        faq.setAnswer(answer);
        faq.setLastEdit(LocalDateTime.now());
    }
    
    // need to make sure the enquiry is from the original owner
    private FAQ retrieveSpecificFAQ(Long faqId, Long adminId) {
        Query query = em.createQuery("SELECT f FROM FAQ f WHERE f.faqId = :faqId AND f.createdBy.adminId = :adminId");
        query.setParameter("faqId", faqId);
        query.setParameter("adminId", adminId);
        
        FAQ faq = (FAQ)query.getSingleResult();
        
        return faq;
    } 

    @Override
    public void deleteFAQ(Long faqId) {
        FAQ deletedFAQ = getFAQ(faqId);
        em.remove(deletedFAQ);
    }
}
