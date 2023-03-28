/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Region;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author muhdm
 */
@Stateless
public class RegionSessionBean implements RegionSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long createNewRegion(Region newRegion) {
        em.persist(newRegion);
        em.flush();

        return newRegion.getRegionId();
    }

    @Override
    public List<Region> retrieveAllRegions() {
        Query query = em.createQuery("SELECT r FROM Region r");
        return query.getResultList();
    }

    @Override
    public Region retrieveRegionById(Long regionId) {
        return em.find(Region.class, regionId);
    }

    @Override
    public Region retrieveRegionByName(String name) {
        Query query = em.createQuery("SELECT r FROM Region r WHERE r.name = :name");
        query.setParameter(name, "name");

        return (Region) query.getSingleResult();
    }

}
