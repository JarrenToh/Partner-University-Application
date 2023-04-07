/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Faculty;
import entity.NUSModule;
import entity.PUModule;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jarrentoh
 */
@Stateless
public class NUSModuleSessionBean implements NUSModuleSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    //CREATE
    @Override
    public Long createNUSModule(NUSModule nusModule, Long facultyId) {
        Faculty faculty = em.find(Faculty.class, facultyId);
        nusModule.setFaculty(faculty);
        em.persist(nusModule);
        faculty.getModules().add(nusModule);
        em.flush();
        //Add relationship between PUModule and NUSModule
        return nusModule.getModuleId();
    }

    //RETRIEVE
    @Override
    public List<NUSModule> retrieveAllNUSModule() {
        Query q = em.createQuery("SELECT nm FROM NUSModule nm");
        return q.getResultList();
    }

    @Override
    public NUSModule retrieveNUSModuleByModuleId(Long moduleId) {
        return em.find(NUSModule.class, moduleId);
    }

    @Override
    public List<NUSModule> searchNUSModuleByCode(String code) {
        Query q = em.createQuery("SELECT nm FROM NUSModule nm WHERE LOWER(nm.code) LIKE :code");
        q.setParameter("code", "%" + code + "%");
        return q.getResultList();
    }

    @Override
    public List<NUSModule> retrieveNUSModuleByFaculty(Faculty faculty) {
        Faculty f = em.find(Faculty.class, faculty.getFacultyId());
        return f.getModules();
    }

    @Override
    public List<NUSModule> searchNUSModuleByFaculty(Faculty faculty) {
        if (faculty.getName() != null) {
            Query q = em.createQuery("SELECT DISTINCT nm FROM NUSModule nm, Faculty f WHERE nm MEMBER OF f.modules AND LOWER(f.name) = :name");
            q.setParameter("name", faculty.getName().toLowerCase());
            return q.getResultList();
        } else {

            return new ArrayList<>();
        }
    }

    @Override
    public List<NUSModule> searchNUSModuleByPUModule(PUModule puModule) {
        if (puModule.getCode() != null) {
            Query q = em.createQuery("SELECT DISTINCT nm FROM NUSModule nm, PUModule pm WHERE pm MEMBER OF nm.puModules AND LOWER(pm.code) = :code");
            q.setParameter("code", "%" + puModule.getCode().toLowerCase() + "%");
            return q.getResultList();
        } else {

            return new ArrayList<>();
        }

    }

    //UPDATE
    @Override
    public Long updateNUSModule(NUSModule module) {
        NUSModule moduleToBeUpdated = em.find(NUSModule.class, module.getModuleId());
        moduleToBeUpdated.setDescription(module.getDescription());
        moduleToBeUpdated.setCode(module.getCode());
        return moduleToBeUpdated.getModuleId();
    }

    //DELETE
    @Override
    public Long deleteNUSModule(NUSModule module) {
        NUSModule nm = em.find(NUSModule.class, module.getModuleId());
        em.remove(nm);
        return module.getModuleId(); // Return the id of the deleted module
    }

}
