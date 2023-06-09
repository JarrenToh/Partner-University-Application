/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful.student;

import ejb.session.stateless.ForumTopicSessionBeanLocal;
import entity.ForumTopic;
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
import util.formRequestEntity.AdminForumTopicRequest;
import util.formRequestEntity.ForumTopicRequest;

/**
 * REST Web Service
 *
 * @author kathleen
 */
@Path("forumTopics")
@RequestScoped
public class ForumTopicsResource {

    @EJB
    private ForumTopicSessionBeanLocal forumTopicSessionBeanLocal;

    @POST
    @Path("/student/{studentId}/{puId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createForumTopic(ForumTopic t, @PathParam("studentId") Long studentId, @PathParam("puId") Long puId, @Context UriInfo uriInfo) {

        forumTopicSessionBeanLocal.createNewForumTopic(t, studentId, puId);

        URI createdUri = uriInfo.getAbsolutePathBuilder()
                .path(t.getTopicId().toString())
                .build();

        return Response.created(createdUri)
                .entity(t)
                .build();
    }

    @POST
    @Path("/user/student/{studentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userCreateForumTopic(@PathParam("studentId") Long studentId,
            ForumTopicRequest request) {

        ForumTopic forumTopic = new ForumTopic();
        forumTopic.setTopicName(request.getTopicName());
        forumTopic.setIsInappropriate(false);
        forumTopic.setIsEdited(false);

        forumTopicSessionBeanLocal.createNewForumTopic(forumTopic, studentId, request.getPuId());

        URI createdUri = UriBuilder.fromResource(ForumTopicsResource.class)
                .path(forumTopic.getTopicId().toString())
                .build();

        return Response.created(createdUri).entity(forumTopic).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllForumTopics() {
        List<ForumTopic> forumTopics = forumTopicSessionBeanLocal.retrieveAllForumTopics();

        GenericEntity<List<ForumTopic>> entity = new GenericEntity<List<ForumTopic>>(forumTopics) {
        };

        return Response.status(200).entity(
                entity
        ).build();
    }

    @GET
    @Path("/pu/{puId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getForumTopicsByPu(@PathParam("puId") Long puId) {
        List<ForumTopic> forumTopics = forumTopicSessionBeanLocal.retrieveForumTopicsByPuId(puId);

        GenericEntity<List<ForumTopic>> entity = new GenericEntity<List<ForumTopic>>(forumTopics) {
        };

        return Response.status(200).entity(
                entity
        ).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getForumTopic(@PathParam("id") Long tId) {
        ForumTopic forumTopic = forumTopicSessionBeanLocal.retrieveForumTopicById(tId);

        if (forumTopic == null) {
            return Response.status(200).entity(new ResponseObject("404")).type(MediaType.APPLICATION_JSON).build();
        }

        return Response.status(200).entity(
                forumTopic
        ).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/student/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getForumTopicsByStudentId(@PathParam("studentId") Long sId) {
        List<ForumTopic> forumTopics = forumTopicSessionBeanLocal.retrieveForumTopicsByStudentId(sId);

        GenericEntity<List<ForumTopic>> entity = new GenericEntity<List<ForumTopic>>(forumTopics) {
        };

        return Response.status(200).entity(
                entity
        ).build();
    }

    @GET
    @Path("/pu/{puId}/student/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPuForumTopicsByStudentId(@PathParam("puId") Long puId, @PathParam("studentId") Long sId) {
        List<ForumTopic> forumTopics = forumTopicSessionBeanLocal.retrievePUForumTopicsByStudentId(puId, sId);

        GenericEntity<List<ForumTopic>> entity = new GenericEntity<List<ForumTopic>>(forumTopics) {
        };

        return Response.status(200).entity(
                entity
        ).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editForumTopic(@PathParam("id") Long tId, ForumTopicRequest forumTopicRequest) {
        ForumTopic forumTopic = forumTopicSessionBeanLocal.retrieveForumTopicById(tId);

        forumTopic.setTopicName(forumTopicRequest.getTopicName());
        forumTopicSessionBeanLocal.editForumTopic(forumTopic);
        return Response.status(204).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteForumTopic(@PathParam("id") Long tId) {
        forumTopicSessionBeanLocal.deleteForumTopic(tId);
        return Response.status(204).build();
    }

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchForumTopic(@QueryParam("topicName") String topicName) {
        if (topicName != null) {
            List<ForumTopic> results
                    = forumTopicSessionBeanLocal.searchForumTopics(topicName);
            GenericEntity<List<ForumTopic>> entity = new GenericEntity<List<ForumTopic>>(results) {
            };
            return Response.status(200).entity(entity).build();
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "No query conditions").build();
            return Response.status(400).entity(exception).build();
        }
    }

    @GET
    @Path("/query/pu/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchForumTopicsByPu(@QueryParam("topicName") String topicName, @PathParam("id") Long puId) {
        if (topicName != null) {
            List<ForumTopic> results
                    = forumTopicSessionBeanLocal.searchForumTopicsByPu(topicName, puId);
            GenericEntity<List<ForumTopic>> entity = new GenericEntity<List<ForumTopic>>(results) {
            };
            return Response.status(200).entity(entity).build();
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "No query conditions").build();
            return Response.status(400).entity(exception).build();
        }
    }

    @GET
    @Path("/query/student/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchForumTopicsByStudent(@QueryParam("topicName") String topicName, @PathParam("id") Long studentId) {
        if (topicName != null) {
            List<ForumTopic> results
                    = forumTopicSessionBeanLocal.searchForumTopicsByStudent(topicName, studentId);
            GenericEntity<List<ForumTopic>> entity = new GenericEntity<List<ForumTopic>>(results) {
            };
            return Response.status(200).entity(entity).build();
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "No query conditions").build();
            return Response.status(400).entity(exception).build();
        }
    }

    @GET
    @Path("/query/student/{id}/pu/{puId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchForumTopicsByStudentAndPu(@QueryParam("topicName") String topicName, @PathParam("id") Long studentId, @PathParam("puId") Long puId) {
        if (topicName != null) {
            List<ForumTopic> results
                    = forumTopicSessionBeanLocal.searchForumTopicsByStudentAndPu(topicName, studentId, puId);
            GenericEntity<List<ForumTopic>> entity = new GenericEntity<List<ForumTopic>>(results) {
            };
            return Response.status(200).entity(entity).build();
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "No query conditions").build();
            return Response.status(400).entity(exception).build();
        }
    }

    @PUT
    @Path("/report/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response reportForumTopic(@PathParam("id") Long tId) {

        forumTopicSessionBeanLocal.reportForumTopic(tId);
        return Response.status(204).build();

    }

}
