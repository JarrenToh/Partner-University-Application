/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful.student;

import ejb.session.stateless.PUSessionBeanLocal;
import entity.PU;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author muhdm
 */
@Path("pu")
@RequestScoped
public class PUResource {

    @EJB
    private PUSessionBeanLocal pUSessionBeanLocal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPus() {

        List<PU> results = pUSessionBeanLocal.retrieveAllPus();
        GenericEntity<List<PU>> entity = new GenericEntity<List<PU>>(results) {
        };

        return Response.status(200).entity(
                entity
        ).build();
    }

    @GET
    @Path("/getPUById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPUbyId(@PathParam("id") Long puId) {

        try {
            PU pu = pUSessionBeanLocal.retrievePuById(puId);
            return Response.status(200).entity(pu).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("/getPUByName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPUbyName(@PathParam("name") String puName) {

        try {
            PU pu = pUSessionBeanLocal.retrievePuByName(puName);
            return Response.status(200).entity(pu).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PU createPU(PU pu) {
        pUSessionBeanLocal.createNewPu(pu);
        return pu;
    }

}
