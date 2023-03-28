/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ForumTopic;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kathleen
 */
@Local
public interface ForumTopicSessionBeanLocal {
    
    public void createNewForumTopic(ForumTopic forumTopic, Long studentId);
    
    public void updateForumTopic(ForumTopic forumTopic);
    
    public void deleteForumTopic(Long forumTopicId);
    
    public List<ForumTopic> retrieveAllForumTopics();
    
    public ForumTopic retrieveForumTopicById(Long forumTopicId);
    
    public List<ForumTopic> searchForumTopics(String topicName);
    
}
