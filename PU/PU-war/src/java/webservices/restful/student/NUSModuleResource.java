/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful.student;

import ejb.session.stateless.NUSModuleSessionBeanLocal;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import ejb.session.stateless.FacultySessionBeanLocal;
import ejb.session.stateless.NUSModuleSessionBeanLocal;
import ejb.session.stateless.PUModuleSessionBeanLocal;
import entity.Faculty;
import entity.NUSModule;
import entity.PUModule;
import entity.PUReview;
import error.NoResultException;
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

/**
 *
 * @author jarrentoh
 */
@Path("nusmodule")
@RequestScoped
public class NUSModuleResource {

    @EJB
    private NUSModuleSessionBeanLocal nusModuleSessionBeanLocal;

    @EJB
    private FacultySessionBeanLocal facultySessionBeanLocal;

    @EJB
    private PUModuleSessionBeanLocal pUModuleSessionBeanLocal;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public NUSModule createNUSModule(NUSModule nm) {
        nm.setCode("IS4303");
        nusModuleSessionBeanLocal.createNUSModule(nm, 1L);
        return nm;
    }

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchNUSModule(@QueryParam("facultyId") Long fId,
            @QueryParam("puModuleId") Long puId) {

        if (fId != null) {

            Faculty f = facultySessionBeanLocal.retrieveFacultyById(fId);
            List<NUSModule> results = nusModuleSessionBeanLocal.searchNUSModuleByFaculty(f);
            GenericEntity<List<NUSModule>> entity = new GenericEntity<List<NUSModule>>(results) {
            };

            return Response.status(200).entity(
                    entity
            ).build();
        } else if (puId != null) {

            try {
                PUModule puMod = pUModuleSessionBeanLocal.getPUModule(puId);
                List<NUSModule> results = nusModuleSessionBeanLocal.searchNUSModuleByPUModule(puMod);
                GenericEntity<List<NUSModule>> entity = new GenericEntity<List<NUSModule>>(results) {
                };

                return Response.status(200).entity(
                        entity
                ).build();
            } catch (NoResultException ex) {
                JsonObject exception = Json.createObjectBuilder()
                    .add("error", "No such PU Module found")
                    .build();

            return Response.status(400).entity(exception).build();
            }

        } else {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "No query conditions")
                    .build();

            return Response.status(400).entity(exception).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllNUSModule() {

        List<NUSModule> results = nusModuleSessionBeanLocal.retrieveAllNUSModule();
        GenericEntity<List<NUSModule>> entity = new GenericEntity<List<NUSModule>>(results) {
        };
        return Response.status(200).entity(
                entity
        ).build();

    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editNUSModule(@PathParam("id") Long id, NUSModule nm) {
        nm.setModuleId(id);
        nm.setCode("LA123");
        nm.setDescription("Blah blah");
        try {
            nusModuleSessionBeanLocal.updateNUSModule(nm);
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
    public Response deleteNUSModule(@PathParam("id") Long id) {
        try {
            NUSModule nm = nusModuleSessionBeanLocal.retrieveNUSModuleByModuleId(id);
            nusModuleSessionBeanLocal.deleteNUSModule(nm);
            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }
}
