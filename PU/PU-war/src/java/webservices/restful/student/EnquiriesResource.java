/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful.student;

import ejb.session.stateless.enquiry.EnquirySessionBeanLocal;
import ejb.session.stateless.enquiry.StudentEnquirySessionBeanLocal;
import entity.Enquiry;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.dataTransferObject.DTOUtility;
import util.dataTransferObject.EnquiryDTO;
import util.enumeration.StatusName;

import util.formRequestEntity.StudentEnquiryRequest;

/**
 * REST Web Service
 *
 * @author wjahoward
 */
@Path("student/enquiries")
@RequestScoped
public class EnquiriesResource {

    @EJB
    private StudentEnquirySessionBeanLocal studentEnquirySessionBeanLocal;

    @EJB
    private EnquirySessionBeanLocal enquirySessionBeanLocal;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEnquiry(@QueryParam("studentId") Long studentId,
                                  StudentEnquiryRequest enquiryRequest, 
                                  @Context UriInfo uriInfo) {
        
        String title = enquiryRequest.getTitle();
        String content = enquiryRequest.getContent();

        Enquiry e = new Enquiry(title, content);

        Enquiry createdEnquiry = studentEnquirySessionBeanLocal.createEnquiry(e, studentId);

        // Create the URI of the newly created resource
        URI createdUri = uriInfo.getAbsolutePathBuilder()
                .path(createdEnquiry.getEnquiryId().toString())
                .build();

        // Return a 201 Created response, along with the new resource's URI and the created FAQ object
        return Response.created(createdUri)
                .entity(createdEnquiry)
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveEnquiry(@PathParam("id") Long eId) {
        Enquiry enquiry = enquirySessionBeanLocal.retrieveEnquiry(eId);

        List<EnquiryDTO> enquiryDTO = DTOUtility.serializeEnquiry(Arrays.asList(enquiry));
        return Response.status(StatusName.OK.getCode()).entity(enquiryDTO).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveOwnEnquires(@QueryParam("studentId") Long studentId) {
        List<Enquiry> enquires = studentEnquirySessionBeanLocal.retrieveOwnEnquires(studentId);

        List<EnquiryDTO> enquiryDTOs = DTOUtility.serializeEnquiry(enquires);

        return Response.status(StatusName.OK.getCode()).entity(enquiryDTOs).build();
    }
    
    // /student/enquiries/1?studentId=1
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editEnquiry(@PathParam("id") Long eId, 
                                @QueryParam("studentId") Long studentId,
                                StudentEnquiryRequest enquiry) {
        String title = enquiry.getTitle();
        String content = enquiry.getContent();
        
        studentEnquirySessionBeanLocal.updateEnquiry(eId, studentId, title, content);
        return Response.status(204).build();
    }
    
    // /student/enquires/1?studentId=1
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEnquiry(@PathParam("id") Long eId, 
                                  @QueryParam("studentId") Long studentId) {
        try {
            studentEnquirySessionBeanLocal.deleteEnquiry(eId, studentId);
            return Response.status(StatusName.NO_CONTENT.getCode()).build();
        } catch (Exception ex) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Enquiry not found")
                    .build();

            return Response.status(StatusName.NOT_FOUND.getCode()).entity(exception).build();
        }
    }
}
