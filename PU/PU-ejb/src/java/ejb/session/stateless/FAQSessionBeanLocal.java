/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FAQ;
import error.NoResultException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wjahoward
 */
@Local
public interface FAQSessionBeanLocal {
    
    public FAQ createFAQ(FAQ faq, Long adminId) throws NoResultException;
    
    public FAQ retrieveFAQ(Long faqId);
    
    public List<FAQ> retrieveAllFAQs();
        
    public void updateFAQ(Long faqId, Long adminId, String question, String answer);
    
    public void deleteFAQ(Long faqId);
}
