/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless.enquiry;

import entity.Enquiry;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author wjahoward
 */
@Stateless
public class EnquirySessionBean implements EnquirySessionBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    private EntityManager em;

    @Override
    public Enquiry retrieveEnquiry(Long enquiryId) {
        Enquiry enquiry = em.find(Enquiry.class, enquiryId);
        return enquiry;
    }
}
