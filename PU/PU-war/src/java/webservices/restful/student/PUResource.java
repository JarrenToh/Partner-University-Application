/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful.student;

import ejb.session.stateless.CountrySessionBeanLocal;
import ejb.session.stateless.PUSessionBeanLocal;
import entity.Country;
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
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.dataTransferObject.PUDTO;
import util.dataTransferObject.ResponseObject;
import util.enumeration.StatusName;
import util.formRequestEntity.PUUpdateRequest;

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
    
    @EJB
    private CountrySessionBeanLocal countrySessionBeanLocal;

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
    
    @GET
    @Path("/getPUByNameAdmin/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPUbyNameAdmin(@PathParam("name") String puName) {

        try {            
            PU pu = pUSessionBeanLocal.retrievePuByName(puName);
            
            if (pu == null) {
                return Response.status(200).entity(new ResponseObject("404")).type(MediaType.APPLICATION_JSON).build();
            }
            
            Country country = pu.getCountry();
            
            PUDTO puDTO = new PUDTO(
                    pu.getPuId(),
                    pu.getName(),
                    pu.getDescription(),
                    pu.getImages(),
                    country.getCountryId()
            );
            return Response.status(200).entity(puDTO).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            
            return Response.status(200).entity(new ResponseObject("404")).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("/mappableModule/{puName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPUModuleMappableModule(@PathParam("puName") String puName) {
        List<Object> results = pUSessionBeanLocal.getMappableModulesGroupedByFaculty(puName);
        GenericEntity<List<Object>> entity = new GenericEntity<List<Object>>(results) {
        };
        return Response.status(200).entity(
                entity
        ).build();
    } //end getPUModule

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PU createPU(PU pu) {
        pUSessionBeanLocal.createNewPu(pu);
        return pu;
    }

    @POST
    @Path("/createPUWithCountry")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPUWithCountry(PUUpdateRequest puUpdateRequest) {

        String name = puUpdateRequest.getName();
        String description = puUpdateRequest.getDescription();
        String images = puUpdateRequest.getImages();
        Long countryId = puUpdateRequest.getCountryId();
        
        Country country = countrySessionBeanLocal.retrieveCountryById(countryId);

        PU pu = new PU();
        pu.setName(name);
        pu.setDescription(description);
        pu.setImages(images);
        pu.setCountry(country);

        pUSessionBeanLocal.createNewPu(pu);
        return Response.status(Response.Status.CREATED).entity(pu).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePU(@PathParam("id") Long puId) {
        try {
            pUSessionBeanLocal.deletePU(puId);
            return Response.status(StatusName.NO_CONTENT.getCode()).build();
        } catch (Exception ex) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "PU not found")
                    .build();

            return Response.status(StatusName.NOT_FOUND.getCode()).entity(exception).build();
        }
    }    
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPU(@PathParam("id") Long puId, 
                            PUUpdateRequest puUpdateRequest) {
        try {
            String name = puUpdateRequest.getName();
            String description = puUpdateRequest.getDescription();
            String images = puUpdateRequest.getImages();
            Long countryId = puUpdateRequest.getCountryId();
            
            pUSessionBeanLocal.updatePUAdmin(puId, name, description, images, countryId);
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
