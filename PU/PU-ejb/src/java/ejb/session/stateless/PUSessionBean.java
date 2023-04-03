/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Country;
import entity.PU;
import entity.Region;
import java.util.ArrayList;
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

    @EJB
    private PUReviewSessionBeanLocal pUReviewSessionBean;

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
//        Query query = em.createQuery("SELECT p FROM PU p");
//        return query.getResultList();

        Query query = em.createQuery("SELECT p.puId, p.name, p.description, p.images, c.name, c.region.name FROM PU p JOIN p.country c");
        List<Object[]> results = query.getResultList();
        List<PU> pus = new ArrayList<>();
        for (Object[] result : results) {
            PU pu = new PU();

            pu.setPuId((Long) result[0]);

            pu.setRating(pUReviewSessionBean.retrieveRating((Long) result[0]));
            pu.setName((String) result[1]);
            pu.setDescription((String) result[2]);
            pu.setImages((String) result[3]);
            pu.setCountryName((String) result[4]);
            pu.setRegionName((String) result[5]);
            pus.add(pu);
        }
        return pus;
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

    @Override
    public List<Object[]> getMappableModulesGroupedByFaculty(String puName) {
        Query query = em.createQuery(
                "SELECT faculty.name, nus.code, nus.description, puModule.code, puModule.description "
                + "FROM NUSModule nus "
                + "JOIN nus.puModules puModule "
                + "JOIN nus.faculty faculty "
                + "JOIN puModule.pu pu "
                + "WHERE LOWER(pu.name) = :puName "
                + "GROUP BY faculty.name, nus.code, nus.description, puModule.code, puModule.description"
        );
        query.setParameter("puName", puName.toLowerCase());
        return query.getResultList();
    }

}
