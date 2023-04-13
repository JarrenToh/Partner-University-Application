/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful.admin;

import ejb.session.stateless.NUSchangeAdminSessionBeanLocal;
import entity.NUSchangeAdmin;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.formRequestEntity.AdminProfileRequest;
import util.formRequestEntity.LoginRequest;

/**
 * REST Web Service
 *
 * @author wjahoward
 */
@Path("admin")
public class NUSchangeAdminsResource {

    @EJB
    private NUSchangeAdminSessionBeanLocal nuschangeAdminSessionBeanLocal;

    /**
     * Retrieves representation of an instance of
     * webservices.restful.AdminsResource
     *
     * @param loginRequest
     * @return an instance of java.lang.String
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        NUSchangeAdmin admin = nuschangeAdminSessionBeanLocal.login(username, password);

        if (admin != null) {
            return Response.status(200).entity(admin).type(MediaType.APPLICATION_JSON).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Path("/searchAdminByUsername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAdminByUsername(@PathParam("username") String adminUsername) {

        if (adminUsername != null) {
            NUSchangeAdmin nuschangeAdmin = nuschangeAdminSessionBeanLocal.searchAdminByUsername(adminUsername);

            return Response.status(200).entity(
                    nuschangeAdmin
            ).build();

        } else {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(400).entity(exception).build();
        }
    }

    @POST
    @Path("/searchAdminByPassword/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAdminByPassword(@PathParam("username") String adminUsername, AdminProfileRequest adminProfileRequest) {
        String password = adminProfileRequest.getUpdateString();

        if (adminUsername != null) {
            NUSchangeAdmin nuschangeAdmin = nuschangeAdminSessionBeanLocal.searchAdminByPassword(adminUsername, password);

            return Response.status(200).entity(
                    nuschangeAdmin
            ).build();

        } else {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(400).entity(exception).build();
        }
    }

    @PUT
    @Path("/editAdminByName/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editAdminByName(@PathParam("username") String adminUsername, AdminProfileRequest adminProfileRequest) {

        if (adminUsername != null) {
            String name = adminProfileRequest.getUpdateString();
            nuschangeAdminSessionBeanLocal.editAdminByName(adminUsername, name);

            return Response.status(204).build();

        } else {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(400).entity(exception).build();
        }
    }

    @PUT
    @Path("/editAdminByUsername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editAdminByUsername(@PathParam("username") String adminUsername, AdminProfileRequest adminProfileRequest) {

        if (adminUsername != null) {
            String newUsername = adminProfileRequest.getUpdateString();
            nuschangeAdminSessionBeanLocal.editAdminByUsername(adminUsername, newUsername);

            return Response.status(204).build();

        } else {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(400).entity(exception).build();
        }
    }

    @PUT
    @Path("/editAdminByPassword/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editAdminByPassword(@PathParam("username") String adminUsername, AdminProfileRequest adminProfileRequest) {

        if (adminUsername != null) {
            String password = adminProfileRequest.getUpdateString();
            nuschangeAdminSessionBeanLocal.editAdminByPassword(adminUsername, password);

            return Response.status(204).build();

        } else {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(400).entity(exception).build();
        }
    }
}
