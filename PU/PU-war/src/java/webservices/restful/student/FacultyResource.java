/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful.student;

import ejb.session.stateless.FacultySessionBeanLocal;
import entity.Faculty;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author jarrentoh
 */
@Path("faculty")
@RequestScoped
public class FacultyResource {

    @EJB
    private FacultySessionBeanLocal facultySessionBeanLocal;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Faculty createFaculty(Faculty f) {
        facultySessionBeanLocal.createFaculty(f);
        return f;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFaculty() {

        List<Faculty> results = facultySessionBeanLocal.retrieveAllFaculty();
        GenericEntity<List<Faculty>> entity = new GenericEntity<List<Faculty>>(results) {
        };

        return Response.status(200).entity(
                entity
        ).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFaculty(@PathParam("id") Long fId) {
        try {
            Faculty f = facultySessionBeanLocal.retrieveFacultyById(fId);
            return Response.status(200).entity(
                    f
            ).type(MediaType.APPLICATION_JSON).build();
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
    public Response deleteFaculty(@PathParam("id") Long fId) {
        try {
            Faculty f = facultySessionBeanLocal.retrieveFacultyById(fId);
            facultySessionBeanLocal.deleteFaculty(f);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }
}
