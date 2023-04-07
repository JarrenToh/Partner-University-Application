/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful.student;

import ejb.session.stateless.CountrySessionBeanLocal;
import entity.Country;
import entity.PU;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
@Path("country")
@RequestScoped
public class CountryResource {

    @EJB
    private CountrySessionBeanLocal countrySessionBeanLocal;
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCountries() {

        List<Country> results = countrySessionBeanLocal.retrieveAllCountries();
        GenericEntity<List<Country>> entity = new GenericEntity<List<Country>>(results) {
        };

        return Response.status(200).entity(
                entity
        ).build();
    }
    
    @GET
    @Path("/getCountryById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountryById(@PathParam("id") Long countryId) {

        try {
            Country country = countrySessionBeanLocal.retrieveCountryById(countryId);
            return Response.status(200).entity(
                    country
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
    @Path("/getCountryByName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountryByName(@PathParam("name") String countryName) {

        try {
            Country country = countrySessionBeanLocal.retrieveCountryByName(countryName);
            return Response.status(200).entity(
                    country
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
    public Country createCountry(Country country) {
        countrySessionBeanLocal.createNewCountry(country);
        return country;
    }

}
