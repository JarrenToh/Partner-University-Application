/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful.student;

import ejb.session.stateless.ForumCommentSessionBeanLocal;
import entity.ForumComment;
import entity.ForumPost;
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
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editForumComment(@PathParam("id") Long cId, ForumComment forumComment) {

        forumComment.setCommentId(cId);
        forumCommentSessionBeanLocal.updateForumComment(forumComment);
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
        return Response.status(200).entity(
                forumComment
        ).type(MediaType.APPLICATION_JSON).build();
    }
    
    @PUT
    @Path("/like/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response likeForumComment(@PathParam("id") Long cId) {

        forumCommentSessionBeanLocal.likeForumComment(cId);
        return Response.status(204).build();

    }
    
    @PUT
    @Path("/unlike/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response unlikeForumComment(@PathParam("id") Long cId) {

        forumCommentSessionBeanLocal.unlikeForumComment(cId);
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
    @Path("/dislike/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response dislikeForumComment(@PathParam("id") Long cId) {

        forumCommentSessionBeanLocal.dislikeForumComment(cId);
        return Response.status(204).build();

    }   
    
    @PUT
    @Path("/undislike/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response undislikeForumComment(@PathParam("id") Long cId) {

        forumCommentSessionBeanLocal.undislikeForumComment(cId);
        return Response.status(204).build();

    }   
}
