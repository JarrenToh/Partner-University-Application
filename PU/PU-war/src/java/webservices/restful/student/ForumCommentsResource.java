/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful.student;

import ejb.session.stateless.ForumCommentSessionBeanLocal;
import entity.ForumComment;
import java.net.URI;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import util.dataTransferObject.ResponseObject;
import util.formRequestEntity.AdminForumCommentRequest;
import util.formRequestEntity.ForumCommentRequest;

/**
 * REST Web Service
 *
 * @author kathleen
 */
@Path("forumComments")
@RequestScoped
public class ForumCommentsResource {

    @EJB 
    private ForumCommentSessionBeanLocal forumCommentSessionBeanLocal;

    @POST
    @Path("/forumPosts/{forumPost_id}/student/{studentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createForumComment(ForumComment c, @PathParam("forumPost_id") Long forumPostId, @PathParam("studentId") Long studentId, @Context UriInfo uriInfo) {
        
        forumCommentSessionBeanLocal.createNewForumComment(c, forumPostId, studentId);
        
        URI createdUri = uriInfo.getAbsolutePathBuilder()
                .path(c.getCommentId().toString())
                .build();
        
        return Response.created(createdUri)
                .entity(c)
                .build();
    }
    
    @POST
    @Path("/user/forumPosts/{forumPost_id}/student/{studentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userCreateForumComment(@PathParam("forumPost_id") Long forumPostId, @PathParam("studentId") Long studentId, ForumCommentRequest forumCommentRequest) {
         
        ForumComment forumComment = new ForumComment();
        forumComment.setMessage(forumCommentRequest.getMessage());
        forumComment.setIsInappropriate(false);
        forumComment.setNoOfDislikes(0);
        forumComment.setNoOfLikes(0);
        forumComment.setIsEdited(false);
        forumComment.setIsAReply(false);
        
        forumCommentSessionBeanLocal.createNewForumComment(forumComment, forumPostId, studentId);
        
        URI createdUri = UriBuilder.fromResource(ForumCommentsResource.class)
                                   .path(forumComment.getCommentId().toString())
                                   .build();
        
        return Response.created(createdUri).entity(forumComment).build();
    }
    
    
    @POST
    @Path("/user/comment/{commentId}/student/{studentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userCreateForumReply(@PathParam("commentId") Long replyingToCommentId, @PathParam("studentId") Long studentId, ForumCommentRequest forumCommentRequest) {
        
        ForumComment forumComment = new ForumComment();
        forumComment.setMessage(forumCommentRequest.getMessage());
        forumComment.setIsInappropriate(false);
        forumComment.setNoOfDislikes(0);
        forumComment.setNoOfLikes(0);
        forumComment.setIsEdited(false);
        forumComment.setIsAReply(true);
        
        forumCommentSessionBeanLocal.replyForumComment(forumComment, replyingToCommentId, studentId);
        
        URI createdUri = UriBuilder.fromResource(ForumCommentsResource.class)
                                   .path(forumComment.getCommentId().toString())
                                   .build();
        
        return Response.created(createdUri).entity(forumComment).build();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editForumComment(@PathParam("id") Long cId, ForumCommentRequest forumCommentRequest) {
        
        ForumComment forumComment = forumCommentSessionBeanLocal.retrieveForumCommentById(cId);
        forumComment.setMessage(forumCommentRequest.getMessage());
        forumCommentSessionBeanLocal.editForumComment(forumComment);
        return Response.status(204).build();

    }
    
    @PUT
    @Path("/editForumCommentByAdmin/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editForumCommentByAdmin(@PathParam("id") Long cId, AdminForumCommentRequest adminForumCommentRequest) {
        
        ForumComment forumComment = forumCommentSessionBeanLocal.retrieveForumCommentById(cId);
        forumComment.setMessage(adminForumCommentRequest.getMessage());
        forumComment.setNoOfLikes(adminForumCommentRequest.getNoOfLikes());
        forumComment.setNoOfDislikes(adminForumCommentRequest.getNoOfDislikes());
        forumComment.setIsInappropriate(adminForumCommentRequest.getIsInappropriate());
        forumCommentSessionBeanLocal.editForumCommentByAdmin(forumComment);
        return Response.status(204).build();

    }
    
    @Path("/showReplies/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateShowReply(@PathParam("id") Long cId) {
        
       forumCommentSessionBeanLocal.updateShowReply(cId);
       return Response.status(204).build();

    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteForumComment(@PathParam("id") Long cId) {
        forumCommentSessionBeanLocal.deleteForumComment(cId);
        return Response.status(204).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllForumComments() {
        List<ForumComment> forumComments = forumCommentSessionBeanLocal.retrieveAllForumComments();

        GenericEntity<List<ForumComment>> entity = new GenericEntity<List<ForumComment>>(forumComments) {
        };

        return Response.status(200).entity(
                entity
        ).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getForumComment(@PathParam("id") Long cId) {
        ForumComment forumComment = forumCommentSessionBeanLocal.retrieveForumCommentById(cId);
        
        if (forumComment == null) {
            return Response.status(200).entity(new ResponseObject("404")).type(MediaType.APPLICATION_JSON).build();
        }
        
        return Response.status(200).entity(
                forumComment
        ).type(MediaType.APPLICATION_JSON).build();
    }
        
    @PUT
    @Path("/like/{commentId}/{studentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response likeForumComment(@PathParam("commentId") Long cId, @PathParam("studentId") Long sId) {

        forumCommentSessionBeanLocal.likeForumComment(cId, sId);
        return Response.status(204).build();

    }
    
    @PUT
    @Path("/unlike/{commentId}/{studentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response unlikeForumComment(@PathParam("commentId") Long cId, @PathParam("studentId") Long sId) {

        forumCommentSessionBeanLocal.unlikeForumComment(cId, sId);
        return Response.status(204).build();

    }
    
    @PUT
    @Path("/report/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response reportForumComment(@PathParam("id") Long cId) {

        forumCommentSessionBeanLocal.reportForumComment(cId);
        return Response.status(204).build();

    }   
    
    @PUT
    @Path("/resolve/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response resolveForumComment(@PathParam("id") Long cId) {

        forumCommentSessionBeanLocal.resolveForumComment(cId);
        return Response.status(204).build();

    }   
    
    @PUT
    @Path("/dislike/{commentId}/{studentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response dislikeForumComment(@PathParam("commentId") Long cId, @PathParam("studentId") Long sId) {

        forumCommentSessionBeanLocal.dislikeForumComment(cId, sId);
        return Response.status(204).build();

    }   
    
    @PUT
    @Path("/undislike/{commentId}/{studentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response undislikeForumComment(@PathParam("commentId") Long cId, @PathParam("studentId") Long sId) {

        forumCommentSessionBeanLocal.undislikeForumComment(cId, sId);
        return Response.status(204).build();

    }   
    
    @GET
    @Path("/query/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchForumComment(@QueryParam("searchQuery") String searchQuery, @PathParam("postId") Long postId) {
        if (searchQuery != null) {
            List<ForumComment> results
                    = forumCommentSessionBeanLocal.searchForumComment(searchQuery, postId);
            GenericEntity<List<ForumComment>> entity = new GenericEntity<List<ForumComment>>(results) {
            };
            return Response.status(200).entity(entity).build();
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "No query conditions").build();
            return Response.status(400).entity(exception).build();
        }
    }
    
}
