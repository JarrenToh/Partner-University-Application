/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful.student;

import ejb.session.stateless.RegionSessionBean;
import ejb.session.stateless.RegionSessionBeanLocal;
import entity.Country;
import entity.Region;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
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
@Path("region")
@RequestScoped
public class RegionResource {

    @EJB
    private RegionSessionBeanLocal regionSessionBeanLocal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRegion() {

        List<Region> results = regionSessionBeanLocal.retrieveAllRegions();
        GenericEntity<List<Region>> entity = new GenericEntity<List<Region>>(results) {
        };

        return Response.status(200).entity(
                entity
        ).build();
    }

    @GET
    @Path("/getRegionById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRegionById(@PathParam("id") Long regionId) {

        try {
            Region region = regionSessionBeanLocal.retrieveRegionById(regionId);
            return Response.status(200).entity(
                    region
            ).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("/getRegionByName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRegionByName(@PathParam("name") String regionName) {

        try {
            Region region = regionSessionBeanLocal.retrieveRegionByName(regionName);
            return Response.status(200).entity(
                    region
            ).type(MediaType.APPLICATION_JSON).build();
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
    public Region createCountry(Region region) {
        regionSessionBeanLocal.createNewRegion(region);
        return region;
    }

}
