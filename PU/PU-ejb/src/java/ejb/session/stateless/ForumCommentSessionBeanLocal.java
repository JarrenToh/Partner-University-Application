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
    
    public ForumComment createNewForumComment(ForumComment forumComment, Long forumPostId, Long studentId);
    
    public ForumComment createNewForumReply(ForumComment forumReply, Long forumCommentId, Long forumPostId, Long studentId);
    
    public void updateShowReply(Long commentId);
    
    public void updateForumComment(ForumComment forumComment);
    
    public void editForumComment(ForumComment forumComment);
    
    public void editForumCommentByAdmin(ForumComment forumComment);
    
    public void deleteForumComment(Long forumCommentId);
    
    public List<ForumComment> retrieveAllForumComments();
    
    public ForumComment retrieveForumCommentById(Long forumCommentId);
        
    public void reportForumComment(Long forumCommentId);
    
    public void resolveForumComment(Long forumCommentId);
    
    public void likeForumComment(Long forumCommentId, Long studentId);
    
    public void unlikeForumComment(Long forumCommentId, Long studentId);
    
    public void dislikeForumComment(Long forumCommentId, Long studentId);
    
    public void undislikeForumComment(Long forumCommentId, Long studentId);
    
    public void replyForumComment(ForumComment reply, Long replyingToCommentId, Long studentId);
}
