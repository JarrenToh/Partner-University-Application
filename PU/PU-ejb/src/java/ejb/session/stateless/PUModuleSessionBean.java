/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FAQ;
import entity.NUSModule;
import entity.PU;
import entity.PUModule;
import entity.Student;
import error.NoResultException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wayneonn
 */
@Stateless
public class PUModuleSessionBean implements PUModuleSessionBeanLocal {

    @PersistenceContext(unitName = "PU-ejbPU")
    private EntityManager em;

    @Override
    public Long createPUModule(PUModule module, Long puId) {
        PU pu = em.find(PU.class, puId);
        pu.getModules().add(module);
        module.setPu(pu);
        em.persist(module);
        em.flush();
        
        return module.getModuleId();
    }
    
    @Override
    public PUModule getPUModule(Long moduleId) throws NoResultException {
        PUModule module = em.find(PUModule.class, moduleId);

        if (module != null) {
            return module;
        } else {
            throw new NoResultException("Not found");
        }
    }

    @Override
    public List<PUModule> retrieveAllPUModules() {
        Query query = em.createQuery("SELECT p FROM PUModule p");
        return query.getResultList();
    }
    
    @Override
    public List<PUModule> searchPUModuleByCode(String code) {
        Query q;
        if (code != null) {
            q = em.createQuery("SELECT p FROM PUModule p WHERE "
                    + "LOWER(p.code) LIKE :name");
            q.setParameter("name", "%" + code.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT p FROM PUModule p");
        }

        return q.getResultList();
    } //end searchCustomers
    
    @Override
    public List<PUModule> searchPUModuleByDescription(String description) {
        Query q;
        if (description != null) {
            q = em.createQuery("SELECT p FROM PUModule p WHERE "
                    + "LOWER(p.description) LIKE :name");
            q.setParameter("name", "%" + description.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT p FROM PUModule p");
        }

        return q.getResultList();
    } //end searchCustomers

    @Override
    public void updatePUModule(Long moduleId, String code, String description) {
        try {
            PUModule module = getPUModule(moduleId);
            
            module.setCode(code);
            module.setDescription(description);
        } catch (NoResultException ex) {
            Logger.getLogger(PUModuleSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void updatePUModule(PUModule p) throws NoResultException {
        PUModule oldP = getPUModule(p.getModuleId());

        oldP.setCode(p.getCode());
        oldP.setDescription(p.getDescription());
        
    } //end updateCustomer

    @Override
    public void deletePUModule(Long moduleId) throws NoResultException {
        try {
            PUModule moduleToRemove = getPUModule(moduleId);
            em.remove(moduleToRemove);
        } catch (NoResultException ex) {
            Logger.getLogger(PUModuleSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void associatePUModuleNUSModule(Long puModId, Long nusModId) {
        
        System.err.println("PUMODID : " + puModId);
        System.err.println("NUSMODID : " + nusModId);
        
        PUModule pUModule = em.find(PUModule.class, puModId);
        NUSModule nUSModule= em.find(NUSModule.class, nusModId);
        
        pUModule.getMappableModules().add(nUSModule);
        nUSModule.getPuModules().add(pUModule);
    }
}
