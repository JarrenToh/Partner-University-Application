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
public interface StudentEnquirySessionBeanLocal {
    
    public Enquiry createEnquiry(Enquiry enquiry, Long studentId);
        
    public List<Enquiry> retrieveOwnEnquires(Long studentId);
    
    public void updateEnquiry(Long enquiryId, Long studentId, String title, String content);
    
    public void deleteEnquiry(Long enquiryId, Long studentId);
}
