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
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wjahoward
 */
@Local
public interface NUSchangeAdminSessionBeanLocal {
    public NUSchangeAdmin initNewNUSchangeAdmin(NUSchangeAdmin admin);
    
    public NUSchangeAdmin login(String username, String password);
    
    public List<FAQ> retrieveAllFAQsByUserSupportAdmin(Long adminId);
    
    public List<Enquiry> retrieveAllEnquiresByUserSupportAdmin(Long adminId);
    
    public List<ForumTopic> retrieveAllForumTopicsBySystemSupportAdmin(Long adminId);
    
    public NUSchangeAdmin retrieveAdmin(Long adminId);
    
    public NUSchangeAdmin searchAdminByUsername(String adminUsername);
    
    public NUSchangeAdmin searchAdminByPassword(String adminUsername, String password);
    
    public void editAdminByName(String username, String name);
    
    public void editAdminByUsername(String username, String newUsername);
    
    public void editAdminByPassword(String username, String password);
}
