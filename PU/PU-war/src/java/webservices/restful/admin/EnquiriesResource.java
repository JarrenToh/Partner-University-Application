/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful.admin;

import ejb.session.stateless.enquiry.AdminEnquirySessionBeanLocal;
import ejb.session.stateless.enquiry.EnquirySessionBeanLocal;
import entity.Enquiry;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.dataTransferObject.DTOUtility;
import util.dataTransferObject.EnquiryDTO;
import util.dataTransferObject.ResponseObject;
import util.enumeration.StatusName;
import util.formRequestEntity.RespondStudentEnquiryRequest;

/**
 * REST Web Service
 *
 * @author wjahoward
 */
@Path("admin/enquiries")
@RequestScoped
public class EnquiriesResource {

    @EJB
    private AdminEnquirySessionBeanLocal adminEnquirySessionBeanLocal;

    @EJB
    private EnquirySessionBeanLocal enquirySessionBeanLocal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllEnquires() { // all student enquiries
        List<Enquiry> enquires = adminEnquirySessionBeanLocal.retrieveAllEnquiries();
        List<EnquiryDTO> enquiryDTOs = DTOUtility.serializeEnquiry(enquires);

        return Response.status(StatusName.OK.getCode()).entity(enquiryDTOs).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveEnquiry(@PathParam("id") Long eId) {
        Enquiry enquiry = enquirySessionBeanLocal.retrieveEnquiry(eId);

        if (enquiry == null) {
            return Response.status(200).entity(new ResponseObject("404")).type(MediaType.APPLICATION_JSON).build();
        }

        EnquiryDTO enquiryDTO = DTOUtility.serializeSingleEnquiry(enquiry);
        return Response.status(StatusName.OK.getCode()).entity(enquiryDTO).build();
    }

    // /admin/enquiries/assigned?adminId=<adminId>
    @GET
    @Path("/assigned")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAssignedEnquires(@QueryParam("adminId") Long adminId) {
        List<Enquiry> enquires = adminEnquirySessionBeanLocal.retrieveAssignedEnquiries(adminId);

        List<EnquiryDTO> enquiryDTOs = DTOUtility.serializeEnquiry(enquires);

        return Response.status(StatusName.OK.getCode()).entity(enquiryDTOs).build();
    }

    // /admin/enquries/<enquiry_id>/respond
    // pass adminId as part of body
    @PUT
    @Path("/{enquiryId}/respond")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response respondToStudentEnquiry(@PathParam("enquiryId") Long enquiryId,
            @QueryParam("adminId") Long adminId,
            RespondStudentEnquiryRequest respondStudentEnquiryRequest) {
        try {
            String response = respondStudentEnquiryRequest.getResponse();
            int status = respondStudentEnquiryRequest.getStatus();
            adminEnquirySessionBeanLocal.respondToStudentEnquiry(enquiryId, adminId, status, response);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(StatusName.NOT_FOUND.getCode()).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }
}
