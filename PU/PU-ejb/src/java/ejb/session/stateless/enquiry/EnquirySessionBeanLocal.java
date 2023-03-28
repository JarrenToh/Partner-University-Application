/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless.enquiry;

import entity.Enquiry;
import javax.ejb.Local;

/**
 *
 * @author wjahoward
 */
@Local
public interface EnquirySessionBeanLocal {
    public Enquiry retrieveEnquiry(Long enquiryId);
}
