/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless.enquiry;

import entity.Enquiry;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wjahoward
 */
@Local
public interface AdminEnquirySessionBeanLocal {
    
    public List<Enquiry> retrieveAllEnquiries();
    
    public List<Enquiry> retrieveAssignedEnquiries(Long adminId);
    
    public void respondToStudentEnquiry(Long enquiryId, Long adminId, int responseStatus, String response);
}
