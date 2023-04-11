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
    
    public void createNewForumTopic(ForumTopic forumTopic, Long studentId, Long puId);
    
    public void updateForumTopic(ForumTopic forumTopic);
    
    public void editForumTopic(ForumTopic forumTopic);
    
    public void editForumTopicByAdmin(ForumTopic forumTopic);
    
    public void deleteForumTopic(Long forumTopicId);
    
    public List<ForumTopic> retrieveAllForumTopics();
    
    public List<ForumTopic> retrieveForumTopicsByStudentId(Long studentId);
    
    public List<ForumTopic> retrievePUForumTopicsByStudentId(Long puId, Long studentId);
    
    public ForumTopic retrieveForumTopicById(Long forumTopicId);
    
    public List<ForumTopic> searchForumTopics(String topicName);
    
    public List<ForumTopic> searchForumTopicsByStudent(String topicName, Long studentId);
    
    public List<ForumTopic> searchForumTopicsByPu(String topicName, Long puId);
    
    public List<ForumTopic> searchForumTopicsByStudentAndPu(String topicName, Long studentId, Long puId);
    
    public void reportForumTopic(Long topicId);
    
    public List<ForumTopic> retrieveForumTopicsByPuId(Long puId);
    
}
