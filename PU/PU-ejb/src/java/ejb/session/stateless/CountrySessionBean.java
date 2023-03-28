/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Country;
import entity.Region;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author muhdm
 */
@Stateless
public class CountrySessionBean implements CountrySessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private RegionSessionBeanLocal regionSessionBean;

    @Override
    public Long createNewCountry(Country newCountry) {
        em.persist(newCountry);
        em.flush();

        return newCountry.getCountryId();
    }
    
    @Override
    public Long createNewCountry(Country newCountry, Long RegionId) {
        Region region = regionSessionBean.retrieveRegionById(RegionId);
        
        newCountry.setRegion(region);
        region.addCountries(newCountry);
        
        em.persist(newCountry);
        em.flush();

        return newCountry.getCountryId();
    }

    @Override
    public List<Country> retrieveAllCountries() {
        Query query = em.createQuery("SELECT c FROM Country c");
        return query.getResultList();
    }

    @Override
    public Country retrieveCountryById(Long countryId) {
        return em.find(Country.class, countryId);
    }

    @Override
    public Country retrieveCountryByName(String name) {
        Query query = em.createQuery("SELECT c FROM Country c WHERE c.name = :name");
        query.setParameter(name, "name");

        return (Country) query.getSingleResult();
    }
}
