/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful.student;

import ejb.session.stateless.PUReviewSessionBeanLocal;
import ejb.session.stateless.PUSessionBeanLocal;
import entity.PU;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import entity.PUReview;
import entity.PUReview;
import entity.Student;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.dataTransferObject.PUReviewDTO;

/**
 *
 * @author jarrentoh
 */
@Path("pureview")
@RequestScoped
public class PUReviewResource {

    @EJB
    private PUReviewSessionBeanLocal puReviewSessionBeanLocal;


    @EJB
    private PUSessionBeanLocal pUSessionBeanLocal;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPUReview(PUReview puReview, @QueryParam("puId") Long puId, @QueryParam("studentId") Long studentId) {
        Long reviewId = puReviewSessionBeanLocal.createPUReview(puReview, puId, studentId);
        JsonObject response = Json.createObjectBuilder()
                .add("message", "PUReview created with ID " + reviewId)
                .build();
        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPUReview() {

        List<PUReview> results = puReviewSessionBeanLocal.retrieveAllPUReview();
        GenericEntity<List<PUReview>> entity = new GenericEntity<List<PUReview>>(results) {
        };
        return Response.status(200).entity(
                entity
        ).build();

    }

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPUReview(@QueryParam("id") Long id) {

        try {
            PUReview p = puReviewSessionBeanLocal.retrievePUReviewById(id);
            Student student = p.getStudent();
            
            PUReviewDTO puReviewDto = new PUReviewDTO(
                    p.getPuReviewId(),
                    p.getRating(),
                    p.getReview(),
                    p.getNoOfLikes(),
                    p.getNoOfDislikes(),
                    p.getIsInappropriate(),
                    student.getStudentId(),
                    student.getFirstName(),
                    student.getLastName()
            );
            
            return Response.status(200).entity(
                    puReviewDto
            ).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("/pu/{puName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPUReviewByPU(@PathParam("puName") String puName) {

        try {
            PU pu = pUSessionBeanLocal.retrievePuByName(puName);
            List<PUReview> results = puReviewSessionBeanLocal.retrievePUReviewByPU(pu);
            GenericEntity<List<PUReview>> entity = new GenericEntity<List<PUReview>>(results) {
            };
            return Response.status(200).entity(
                    entity
            ).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
        
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReportedPUReviews() {
        List<PUReview> puReviews = puReviewSessionBeanLocal.retrieveReportedPUReview();
        List<PUReviewDTO> puReviewDtos = new ArrayList<>();

        for (PUReview puReview : puReviews) {
            Student student = puReview.getStudent();
            PUReviewDTO puReviewDto = new PUReviewDTO(
                    puReview.getPuReviewId(),
                    puReview.getReview(),
                    puReview.getIsInappropriate(),
                    student.getStudentId(),
                    student.getFirstName(),
                    student.getLastName()
            );
            puReviewDtos.add(puReviewDto);
        }

        return Response.status(200).entity(puReviewDtos).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPUReview(@PathParam("id") Long id, PUReview p) {
        p.setPuReviewId(id);
        try {
            puReviewSessionBeanLocal.updatePUReview(p);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePUReview(@PathParam("id") Long id) {
        try {
            PUReview p = puReviewSessionBeanLocal.retrievePUReviewById(id);
            puReviewSessionBeanLocal.deletePUReview(p);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }
}
