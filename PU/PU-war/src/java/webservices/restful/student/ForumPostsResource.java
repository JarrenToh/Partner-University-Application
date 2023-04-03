/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful.student;

import ejb.session.stateless.ForumPostSessionBeanLocal;
import entity.ForumPost;
import entity.Student;
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

import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import util.formRequestEntity.ForumPostRequest;

/**
 * REST Web Service
 *
 * @author kathleen
 */
@Path("forumPosts")
@RequestScoped
public class ForumPostsResource {

    @EJB 
    private ForumPostSessionBeanLocal forumPostSessionBeanLocal;

    
    @POST
    @Path("/forumTopics/{forumTopic_id}/student/{studentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createForumPost(ForumPost p, @PathParam("forumTopic_id") Long forumTopicId, @PathParam("studentId") Long studentId, @Context UriInfo uriInfo) {
        
        forumPostSessionBeanLocal.createNewForumPost(p, forumTopicId, studentId);
        
        URI createdUri = uriInfo.getAbsolutePathBuilder()
                .path(p.getPostId().toString())
                .build();
        
        return Response.created(createdUri)
                .entity(p)
                .build();
    }
    
    @POST
    @Path("/user/forumTopics/{id}/student/{studentId}/forumPosts")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userCreateForumPost(@PathParam("id") Long forumTopicId, 
                                    @PathParam("studentId") Long studentId,
                                    ForumPostRequest request) {
        
        ForumPost forumPost = new ForumPost();
        forumPost.setTitle(request.getTitle());
        forumPost.setMessage(request.getContent());
        forumPost.setIsInappropriate(false);
        forumPost.setNoOfDislikes(0);
        forumPost.setNoOfLikes(0);
        forumPost.setIsEdited(false);

        forumPostSessionBeanLocal.createNewForumPost(forumPost, forumTopicId, studentId);
        
        URI createdUri = UriBuilder.fromResource(ForumPostsResource.class)
                                   .path(forumPost.getPostId().toString())
                                   .build();
        
        return Response.created(createdUri).entity(forumPost).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editForumPost(@PathParam("id") Long pId, ForumPostRequest forumPostRequest) {
        
        ForumPost forumPost = forumPostSessionBeanLocal.retrieveForumPostById(pId);
        forumPost.setTitle(forumPostRequest.getTitle());
        forumPost.setMessage(forumPostRequest.getContent());
        forumPostSessionBeanLocal.editForumPost(forumPost);
        return Response.status(204).build();

    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteForumPost(@PathParam("id") Long pId) {
        forumPostSessionBeanLocal.deleteForumPost(pId);
        return Response.status(204).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllForumPosts() {
        List<ForumPost> forumPosts = forumPostSessionBeanLocal.retrieveAllForumPosts();

        GenericEntity<List<ForumPost>> entity = new GenericEntity<List<ForumPost>>(forumPosts) {
        };

        return Response.status(200).entity(
                entity
        ).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getForumPost(@PathParam("id") Long pId) {
        ForumPost forumPost = forumPostSessionBeanLocal.retrieveForumPostById(pId);
        return Response.status(200).entity(
                forumPost
        ).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/topic/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getForumPostByTopic(@PathParam("id") Long tId) {
        List<ForumPost> forumPosts = forumPostSessionBeanLocal.retrieveForumPostByTopic(tId);
        
        GenericEntity<List<ForumPost>> entity = new GenericEntity<List<ForumPost>>(forumPosts) {
        };

        return Response.status(200).entity(
                entity
        ).build();
    }
    
    @GET
    @Path("/topic/{topicId}/student/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getForumPostsByTopicAndStudent(@PathParam("topicId") Long tId, @PathParam("studentId") Long sId) {
        List<ForumPost> forumPosts = forumPostSessionBeanLocal.retrieveForumPostsByTopicAndStudent(tId, sId);
        
        GenericEntity<List<ForumPost>> entity = new GenericEntity<List<ForumPost>>(forumPosts) {
        };

        return Response.status(200).entity(
                entity
        ).build();
    }
    
    @PUT
    @Path("/like/{postId}/{studentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response likeForumPost(@PathParam("postId") Long pId, @PathParam("studentId") Long sId) {

        forumPostSessionBeanLocal.likeForumPost(pId, sId);
        return Response.status(204).build();

    }
    
    @PUT
    @Path("/unlike/{postId}/{studentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response unlikeForumPost(@PathParam("postId") Long pId, @PathParam("studentId") Long sId) {

        forumPostSessionBeanLocal.unlikeForumPost(pId, sId);
        return Response.status(204).build();

    }
    
    @PUT
    @Path("/report/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response reportForumPost(@PathParam("id") Long pId) {

        forumPostSessionBeanLocal.reportForumPost(pId);
        return Response.status(204).build();

    }   
    
    @PUT
    @Path("/resolve/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response resolveForumPost(@PathParam("id") Long pId) {

        forumPostSessionBeanLocal.resolveForumPost(pId);
        return Response.status(204).build();

    }   
    
    @PUT
    @Path("/dislike/{postId}/{studentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response dislikeForumPost(@PathParam("postId") Long pId, @PathParam("studentId") Long sId) {

        forumPostSessionBeanLocal.dislikeForumPost(pId, sId);
        return Response.status(204).build();

    }   
    
    @PUT
    @Path("/undislike/{postId}/{studentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response undislikeForumPost(@PathParam("postId") Long pId, @PathParam("studentId") Long sId) {

        forumPostSessionBeanLocal.undislikeForumPost(pId, sId);
        return Response.status(204).build();

    }   
    
    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchForumPost(@QueryParam("postTitle") String postTitle) {
        if (postTitle != null) {
            List<ForumPost> results
                    = forumPostSessionBeanLocal.searchForumPost(postTitle);
            GenericEntity<List<ForumPost>> entity = new GenericEntity<List<ForumPost>>(results) {
            };
            return Response.status(200).entity(entity).build();
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "No query conditions").build();
            return Response.status(400).entity(exception).build();
        }
    }
    
    @GET
    @Path("/query/forumTopics/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchForumPostByTopic(@QueryParam("postTitle") String postTitle, @PathParam("id") Long forumTopicId) {
        if (postTitle != null) {
            List<ForumPost> results
                    = forumPostSessionBeanLocal.searchForumPostByTopic(postTitle, forumTopicId);
            GenericEntity<List<ForumPost>> entity = new GenericEntity<List<ForumPost>>(results) {
            };
            return Response.status(200).entity(entity).build();
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "No query conditions").build();
            return Response.status(400).entity(exception).build();
        }
    }
    
    @GET
    @Path("/query/topic/{topicId}/student/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchForumPostByTopicAndStudent(@QueryParam("postTitle") String postTitle, @PathParam("topicId") Long topicId, @PathParam("studentId") Long studentId) {
        if (postTitle != null) {
            List<ForumPost> results
                    = forumPostSessionBeanLocal.searchForumPostsByTopicAndStudent(postTitle, topicId, studentId);
            GenericEntity<List<ForumPost>> entity = new GenericEntity<List<ForumPost>>(results) {
            };
            return Response.status(200).entity(entity).build();
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "No query conditions").build();
            return Response.status(400).entity(exception).build();
        }
    }
 
}
