package webservices.restful.student;

import ejb.session.stateless.PUModuleReviewSessionBeanLocal;
import ejb.session.stateless.PUModuleSessionBeanLocal;
import ejb.session.stateless.StudentSessionBeanLocal;
import entity.PUModuleReview;
import entity.Student;
import error.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
import util.dataTransferObject.PUModuleReviewDTO;
import util.dataTransferObject.ResponseObject;

@Path("pumodulereview")
@RequestScoped
public class PUModuleReviewResource {

    @EJB
    private StudentSessionBeanLocal studentSessionLocal;

    @EJB
    private PUModuleReviewSessionBeanLocal puModuleReviewSessionBeanLocal;

    @EJB
    private PUModuleSessionBeanLocal pUModuleSessionBeanLocal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPUModuleReviews() {
        List<PUModuleReview> puModuleReviews = puModuleReviewSessionBeanLocal.retrieveAllPUModuleReviews();
        List<PUModuleReviewDTO> puReviewDtos = new ArrayList<>();

        for (PUModuleReview puModuleReview : puModuleReviews) {

            Student student = puModuleReview.getStudent();

            if (student == null) {
                continue;
            }

            PUModuleReviewDTO puModuleReviewDto = new PUModuleReviewDTO(
                    puModuleReview.getModuleReviewId(),
                    puModuleReview.getRating(),
                    puModuleReview.getReview(),
                    puModuleReview.getNoOfLikes(),
                    puModuleReview.getNoOfLikes(),
                    puModuleReview.getIsInappropriate(),
                    student.getStudentId(),
                    student.getFirstName(),
                    student.getLastName()
            );
            puReviewDtos.add(puModuleReviewDto);
        }

        return Response.status(200).entity(puReviewDtos).build();
    }

    @GET
    @Path("from-module/{moduleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPUModuleReviewsFromModule(@PathParam("moduleId") long moduleId) {

        try {
            List<PUModuleReview> puModuleReviews = pUModuleSessionBeanLocal.getPUModule(moduleId).getModuleReviews();
            List<PUModuleReviewDTO> puReviewDtos = new ArrayList<>();

            for (PUModuleReview puModuleReview : puModuleReviews) {

                Student student = puModuleReview.getStudent();

                if (student == null) {
                    continue;
                }

                PUModuleReviewDTO puModuleReviewDto = new PUModuleReviewDTO(
                        puModuleReview.getModuleReviewId(),
                        puModuleReview.getRating(),
                        puModuleReview.getReview(),
                        puModuleReview.getNoOfLikes(),
                        puModuleReview.getNoOfDislikes(),
                        puModuleReview.getIsInappropriate(),
                        student.getStudentId(),
                        student.getFirstName(),
                        student.getLastName()
                );
                puReviewDtos.add(puModuleReviewDto);
            }

            return Response.status(200).entity(puReviewDtos).build();
        } catch (NoResultException ex) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "No query conditions")
                    .build();
            
            return Response.status(400).entity(exception).build();
        }
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

    // in case
//    @GET
//    @Path("/inappropiateReviews")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getAllInappropiateReviews() {
//        List<PUModuleReview> puModuleReviews = puModuleReviewSessionBeanLocal.retrieveAllPUModuleReviews();
//        List<PUModuleReviewDTO> puReviewDtos = new ArrayList<>();
//
//        for (PUModuleReview puModuleReview : puModuleReviews) {
//            
//            if (!puModuleReview.getIsInappropriate()) {
//                continue;
//            }
//            
//            Student student = puModuleReview.getStudent();
//            PUModuleReviewDTO puModuleReviewDto = new PUModuleReviewDTO(
//                    puModuleReview.getModuleReviewId(),
//                    puModuleReview.getReview(),
//                    puModuleReview.getIsInappropriate(),
//                    student.getStudentId(),
//                    student.getFirstName(),
//                    student.getLastName()
//            );
//            puReviewDtos.add(puModuleReviewDto);
//        }
//
//        return Response.status(200).entity(puReviewDtos).build();
//    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPUModuleReview(@PathParam("id") Long cId) {
        try {
            PUModuleReview c = puModuleReviewSessionBeanLocal.getPUModuleReview(cId);
                        
            Student student = c.getStudent();

            PUModuleReviewDTO puModuleReviewDto = new PUModuleReviewDTO(
                    c.getModuleReviewId(),
                    c.getRating(),
                    c.getReview(),
                    c.getNoOfLikes(),
                    c.getNoOfDislikes(),
                    c.getIsInappropriate(),
                    student.getStudentId(),
                    student.getFirstName(),
                    student.getLastName()
            );

            return Response.status(200).entity(
                    puModuleReviewDto
            ).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(200).entity(new ResponseObject("404")).type(MediaType.APPLICATION_JSON).build();
        }
    } //end getPUModuleReview

    @POST
    @Path("/{studentId}/{moduleId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PUModuleReview createPUModuleReview(PUModuleReview s, @PathParam("studentId") Long studentId, @PathParam("moduleId") Long moduleId) {
        puModuleReviewSessionBeanLocal.createPUModuleReview(s, studentId, moduleId);
        return s;
    } //end createPUModuleReview

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
    
    @PUT
    @Path("/like/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editModReviewLike(@QueryParam("studentId") Long studentId, @QueryParam("modReviewId") Long modReviewId, @QueryParam("choice") Integer choice) {
        try {
            puModuleReviewSessionBeanLocal.updatePUModReviewLikedByStudent(modReviewId, studentId, choice);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @PUT 
    @Path("/dislike/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editModReviewdislike(@QueryParam("studentId") Long studentId, @QueryParam("modReviewId") Long modReviewId, @QueryParam("choice") Integer choice) {
        try {
            puModuleReviewSessionBeanLocal.updateModPUReviewDislikedByStudent(modReviewId, studentId, choice);
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
