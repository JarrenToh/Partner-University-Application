/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ForumTopic;
import error.NoResultException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kathleen
 */
@Local
public interface ForumTopicSessionBeanLocal {
    
    public void createNewForumTopic(ForumTopic forumTopic, Long studentId);
    
    public void createNewForumTopicByAdmin(ForumTopic forumTopic, Long adminId) throws NoResultException;
    
    public void updateForumTopic(ForumTopic forumTopic);
    
    public void editForumTopic(ForumTopic forumTopic);
    
    public void editForumTopicByAdmin(ForumTopic forumTopic);
    
    public void deleteForumTopic(Long forumTopicId);
    
    public List<ForumTopic> retrieveAllForumTopics();
    
    public List<ForumTopic> retrieveForumTopicsByStudentId(Long studentId);
    
    public ForumTopic retrieveForumTopicById(Long forumTopicId);
    
    public List<ForumTopic> searchForumTopics(String topicName);
    
    public List<ForumTopic> searchForumTopicsByStudent(String topicName, Long studentId);
    
    public void reportForumTopic(Long topicId);
    
}
