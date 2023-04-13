/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful.admin;

import ejb.session.stateless.ForumTopicSessionBeanLocal;
import entity.ForumTopic;
import error.NoResultException;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.enumeration.StatusName;
import util.formRequestEntity.AdminForumTopicRequest;
import util.formRequestEntity.ForumTopicRequest;

/**
 * REST Web Service
 *
 * @author wjahoward
 * TODO: RUD
 */
@Path("admin/forumTopics")
@RequestScoped
public class ForumTopicsResource {
    
    @EJB
    private ForumTopicSessionBeanLocal forumTopicSessionBeanLocal;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllForumTopics() {
        List<ForumTopic> forumTopics = forumTopicSessionBeanLocal.retrieveAllForumTopics();
        return Response.status(StatusName.OK.getCode()).entity(forumTopics).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createForumTopic(ForumTopicRequest forumTopicRequest, @QueryParam("adminId") Long adminId) {        
        try {
            String topicName = forumTopicRequest.getTopicName();
            
            ForumTopic forumTopic = new ForumTopic();
            forumTopic.setTopicName(topicName);
                                    
            forumTopicSessionBeanLocal.createNewForumTopicByAdmin(forumTopic, adminId);
            return Response.status(Response.Status.CREATED).entity(forumTopic).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Admin not found")
                    .build();

            return Response.status(Response.Status.NOT_FOUND).entity(exception).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editForumTopicByAdmin(@PathParam("id") Long cId, AdminForumTopicRequest adminForumTopicRequest) {
        
        ForumTopic forumTopic = forumTopicSessionBeanLocal.retrieveForumTopicById(cId);
        forumTopic.setTopicName(adminForumTopicRequest.getTopicName());
        forumTopic.setIsInappropriate(adminForumTopicRequest.getIsInappropriate());
        forumTopic.setLastEdit(LocalDateTime.now());
        forumTopicSessionBeanLocal.editForumTopicByAdmin(forumTopic);
        return Response.status(204).build();

    }
}
