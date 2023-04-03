/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Country;
import entity.PU;
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
public class PUSessionBean implements PUSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private CountrySessionBeanLocal countrySessionBean;

    @Override
    public Long createNewPu(PU newPu) {
        em.persist(newPu);
        em.flush();

        return newPu.getPuId();
    }

    //with proper associations
    @Override
    public Long createNewPu(PU newPu, Long countryId, Long regionId) {
        Country country = countrySessionBean.retrieveCountryById(countryId);
        
        newPu.setCountry(country);
        country.addPu(newPu);
        
        em.persist(newPu);
        em.flush();
        return newPu.getPuId();
    }
    
    @Override
    public List<PU> retrieveAllPus() {
        Query query = em.createQuery("SELECT p FROM PU p");
        return query.getResultList();
    }

    @Override
    public PU retrievePuById(Long puId) {
        return em.find(PU.class, puId);
    }

    @Override
    public PU retrievePuByName(String name) {
        Query query = em.createQuery("SELECT p FROM PU p WHERE p.name = :name");
        query.setParameter(name, "name");

        return (PU) query.getSingleResult();
    }

}
