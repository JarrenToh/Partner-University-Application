/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful.admin;

import ejb.session.stateless.ForumTopicSessionBeanLocal;
import entity.ForumTopic;
import java.net.URI;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

    @POST
    @Path("/student/{studentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createForumTopic(ForumTopic t, @PathParam("studentId") Long studentId, @Context UriInfo uriInfo) {
        
        forumTopicSessionBeanLocal.createNewForumTopic(t, studentId);
        
        URI createdUri = uriInfo.getAbsolutePathBuilder()
                .path(t.getTopicId().toString())
                .build();
        
        return Response.created(createdUri)
                .entity(t)
                .build();
    }
}
