/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ForumComment;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kathleen
 */
@Local
public interface ForumCommentSessionBeanLocal {
    
    public void createNewForumComment(ForumComment forumComment, Long forumPostId, Long studentId);
    
    public void updateForumComment(ForumComment forumComment);
    
    public void deleteForumComment(Long forumCommentId);
    
    public List<ForumComment> retrieveAllForumComments();
    
    public ForumComment retrieveForumCommentById(Long forumCommentId);
    
    public void reportForumComment(Long forumCommentId);
    
    public void resolveForumComment(Long forumCommentId);
    
    public void likeForumComment(Long forumCommentId);
    
    public void unlikeForumComment(Long forumCommentId);
    
    public void dislikeForumComment(Long forumCommentId);
    
    public void undislikeForumComment(Long forumCommentId);
}
