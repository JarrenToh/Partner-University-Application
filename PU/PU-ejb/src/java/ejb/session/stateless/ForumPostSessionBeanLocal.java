/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ForumPost;
import entity.ForumTopic;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kathleen
 */
@Local
public interface ForumPostSessionBeanLocal {
    
    public void createNewForumPost(ForumPost forumPost, Long forumTopicId, Long studentId);
    
    public void updateForumPost(ForumPost forumPost);
    
    public void deleteForumPost(Long forumPostId);
    
    public List<ForumPost> retrieveAllForumPosts();
    
    public ForumPost retrieveForumPostById(Long forumPostId);
    
    public List<ForumPost> retrieveAppropriateForumPosts();
    
    public List<ForumPost> retrieveInappropriateForumPosts();
    
    public void likeForumPost(Long forumPostId);
    
    public void unlikeForumPost(Long forumPostId);
    
    public void reportForumPost(Long forumPostId);
    
    public void resolveForumPost(Long forumPostId);
    
    public void dislikeForumPost(Long forumPostId);
    
    public void undislikeForumPost(Long forumPostId);
    
    public List<ForumPost> searchForumPost(String postTitle);
    
    public List<ForumPost> searchForumPostByTopic(String postTitle, Long topicId);
    
    public List<ForumPost> retrieveForumPostByTopic(Long topicId);
    
}
