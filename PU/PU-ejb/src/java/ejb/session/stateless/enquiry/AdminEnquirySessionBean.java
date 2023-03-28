/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless.enquiry;

import entity.Enquiry;
import entity.NUSchangeAdmin;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.EnquiryStatusEnum;
/**
 *
 * @author wjahoward hello
 */
@Stateless
public class AdminEnquirySessionBean implements AdminEnquirySessionBeanLocal {

    @PersistenceContext(unitName = "PU-ejbPU")
    private EntityManager em;
        
    @Override
    public List<Enquiry> retrieveAllEnquiries() {
        Query query = em.createQuery("SELECT e FROM Enquiry e");
        return query.getResultList();
    }
    
    @Override
    public List<Enquiry> retrieveAssignedEnquiries(Long adminId) {
        Query query = em.createQuery("SELECT e FROM Enquiry e WHERE e.nuschangeAdmin.adminId = :adminId");
        query.setParameter("adminId", adminId);
        return query.getResultList();
    }
    
    @Override
    public void respondToStudentEnquiry(Long enquiryId, Long adminId, int responseStatus, String response) {
        Query query = em.createQuery("SELECT e FROM Enquiry e WHERE e.enquiryId = :enquiryId");
        query.setParameter("enquiryId", enquiryId);
        
        Enquiry e = (Enquiry)query.getSingleResult();
        
        query = em.createQuery("SELECT a FROM NUSchangeAdmin a WHERE a.adminId = :adminId");
        query.setParameter("adminId", adminId);
        
        NUSchangeAdmin a = (NUSchangeAdmin)query.getSingleResult();
                
        e.setStatus(EnquiryStatusEnum.RESPONDED);
        
        e.setResponseStatusDate(LocalDateTime.now());
        e.setResponse(response);
        e.setNUSchangeAdmin(a);
    }
}
