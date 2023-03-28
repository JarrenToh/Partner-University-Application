/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful.admin;

import ejb.session.stateless.NUSchangeAdminSessionBeanLocal;
import entity.NUSchangeAdmin;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

        System.out.println("Username is " + username);
        System.out.println("Password is " + password);

        NUSchangeAdmin admin = nuschangeAdminSessionBeanLocal.login(username, password);

        if (admin != null) {
            return Response.ok().build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
