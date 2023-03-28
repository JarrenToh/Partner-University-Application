/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful.admin;

import ejb.session.stateless.FAQSessionBeanLocal;
import ejb.session.stateless.NUSchangeAdminSessionBeanLocal;
import entity.FAQ;
import error.NoResultException;
import java.net.URI;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.enumeration.StatusName;
import util.formRequestEntity.FAQUpdateRequest;

/**
 * REST Web Service
 *
 * @author wjahoward
 * 
 *
 */
@Path("admin/faqs")
@RequestScoped
public class FaqsResource {

    @EJB
    private FAQSessionBeanLocal faqSessionBeanLocal;

    @EJB
    NUSchangeAdminSessionBeanLocal nuschangeAdminSessionBeanLocal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllFAQs() {
        List<FAQ> faqs = faqSessionBeanLocal.retrieveAllFAQs();
        return Response.status(StatusName.OK.getCode()).entity(faqs).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveFAQ(@PathParam("id") Long fId) {
        FAQ faq = faqSessionBeanLocal.retrieveFAQ(fId);
        return Response.status(StatusName.OK.getCode()).entity(faq).build();
    }

    // admin/faqs/ownFaqs?adminId=<adminId>
    @GET
    @Path("/ownFaqs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllFAQsByUserSupportAdmin(@QueryParam("adminId") Long adminId) {
        List<FAQ> faqs = nuschangeAdminSessionBeanLocal.retrieveAllFAQsByUserSupportAdmin(adminId);
        return Response.status(StatusName.OK.getCode()).entity(faqs).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFAQ(@QueryParam("adminId") Long adminId, FAQUpdateRequest faqUpdateRequest) {
        try {
            String question = faqUpdateRequest.getQuestion();
            String answer = faqUpdateRequest.getAnswer();
            
            FAQ faq = new FAQ();
            faq.setQuestion(question);
            faq.setAnswer(answer);
                        
            faqSessionBeanLocal.createFAQ(faq, adminId);
            return Response.status(Response.Status.CREATED).entity(faq).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Admin not found")
                    .build();

            return Response.status(Response.Status.NOT_FOUND).entity(exception).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFAQ(@PathParam("id") Long fId) {
        try {
            faqSessionBeanLocal.deleteFAQ(fId);
            return Response.status(StatusName.NO_CONTENT.getCode()).build();
        } catch (Exception ex) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "FAQ not found")
                    .build();

            return Response.status(StatusName.NOT_FOUND.getCode()).entity(exception).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editFAQ(@PathParam("id") Long fId, 
                            @QueryParam("adminId") Long adminId,
                            FAQUpdateRequest faqUpdateRequest) {
        try {
            String question = faqUpdateRequest.getQuestion();
            String answer = faqUpdateRequest.getAnswer();

            faqSessionBeanLocal.updateFAQ(fId, adminId, question, answer);
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