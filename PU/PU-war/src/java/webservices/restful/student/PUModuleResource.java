package webservices.restful.student;

import ejb.session.stateless.PUModuleSessionBeanLocal;
import ejb.session.stateless.PUModuleSessionBeanLocal;
import entity.PUModule;
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

@Path("pumodule")
@RequestScoped
public class PUModuleResource {

    @EJB
    private PUModuleSessionBeanLocal puModuleSessionBeanLocal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PUModule> getAllPUModules() {
        return puModuleSessionBeanLocal.retrieveAllPUModules();
    }

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchPUModule(@QueryParam("code") String code, @QueryParam("description") String description) {

        if (code != null) {
            List<PUModule> results = puModuleSessionBeanLocal.searchPUModuleByCode(code);
            GenericEntity<List<PUModule>> entity = new GenericEntity<List<PUModule>>(results) {
            };

            return Response.status(200).entity(
                    entity
            ).build();

        } else if (description != null) {
            List<PUModule> results = puModuleSessionBeanLocal.searchPUModuleByDescription(description);
            GenericEntity<List<PUModule>> entity = new GenericEntity<List<PUModule>>(results) {
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
    } //end searchPUModules

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPUModule(@PathParam("id") Long cId) {
        try {
            PUModule c = puModuleSessionBeanLocal.getPUModule(cId);
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
    } //end getPUModule

    
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public PUModule createPUModule(PUModule s) {
//        puModuleSessionBeanLocal.createPUModule(s);
//        return s;
//    } //end createPUModule
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPUModule(@PathParam("id") Long cId, PUModule c) {
        c.setModuleId(cId);
        try {
            puModuleSessionBeanLocal.updatePUModule(c);
            return Response.status(204).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end editPUModule

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePUModule(@PathParam("id") Long studentId) {
        try {
            puModuleSessionBeanLocal.deletePUModule(studentId);
            return Response.status(204).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end deletePUModule

}
