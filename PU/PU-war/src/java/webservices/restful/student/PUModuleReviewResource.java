package webservices.restful.student;

import ejb.session.stateless.PUModuleReviewSessionBeanLocal;
import ejb.session.stateless.StudentSessionBean;
import entity.PUModuleReview;
import error.NoResultException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("pumodulereview")
@RequestScoped
public class PUModuleReviewResource {

    @EJB
    private PUModuleReviewSessionBeanLocal puModuleReviewSessionBeanLocal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PUModuleReview> getAllPUModuleReviews() {
        return puModuleReviewSessionBeanLocal.retrieveAllPUModuleReviews();
    }

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchPUModuleReview(@QueryParam("review") String review) {

        if (review != null) {
            List<PUModuleReview> results = puModuleReviewSessionBeanLocal.searchPUModuleReviewByReview(review);
            GenericEntity<List<PUModuleReview>> entity = new GenericEntity<List<PUModuleReview>>(results) {
            };

            return Response.status(200).entity(
                    entity
            ).build();

        } else {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "No query conditions")
                    .build();

            return Response.status(400).entity(exception).build();
        }
    } //end searchPUModuleReviews
    
    @GET
    @Path("/inappropiateReviews")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PUModuleReview> getAllInappropiateReviews() {
        return puModuleReviewSessionBeanLocal.viewInappropiatePUModuleReview();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPUModuleReview(@PathParam("id") Long cId) {
        try {
            PUModuleReview c = puModuleReviewSessionBeanLocal.getPUModuleReview(cId);
            return Response.status(200).entity(
                    c
            ).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end getPUModuleReview


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PUModuleReview createPUModuleReview(PUModuleReview s) {
        puModuleReviewSessionBeanLocal.createPUModuleReview(s);
        return s;
    } //end createPUModuleReview

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPUModuleReview(@PathParam("id") Long cId, PUModuleReview c) {
        c.setModuleReviewId(cId);
        try {
            puModuleReviewSessionBeanLocal.updatePUModuleReview(c);
            return Response.status(204).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end editPUModuleReview

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePUModuleReview(@PathParam("id") Long studentId) {
        try {
            puModuleReviewSessionBeanLocal.deletePUModuleReview(studentId);
            return Response.status(204).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end deletePUModuleReview

    
    @PUT
    @Path("/toggleInappropiate/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response undislikeForumPost(@PathParam("id") Long sId) {

        try {
        puModuleReviewSessionBeanLocal.toggleInappropiate(sId);
        return Response.status(204).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }

    }   
}
