/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Faculty;
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
public class FacultySessionBean implements FacultySessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    //CREATE
    @Override
    public Long createFaculty(Faculty newFaculty) {
        em.persist(newFaculty);
        em.flush();;
        return newFaculty.getFacultyId();
    }

    //RETRIEVE
    @Override
    public List<Faculty> retrieveAllFaculty() {
        Query q = em.createQuery("SELECT f FROM Faculty f");
        return q.getResultList();
    }
    
    @Override
    public Faculty retrieveFacultyById(Long facultyId) {
        Faculty faculty = em.find(Faculty.class, facultyId);
        return faculty;
    }

    @Override
    public List<Faculty> searchFacultiesByName(String facultyName) {
        
        Query q;
        
        if (facultyName != null) {
            
            q = em.createQuery("SELECT f FROM Faculty f WHERE LOWER(f.name) LIKE :facultyName");
            q.setParameter("facultyName", "%" + facultyName.toLowerCase() + "%");
            return q.getResultList();
        }
        
        return retrieveAllFaculty();
    }

    //UPDATE
    @Override
    public Long updateFaculty(Faculty faculty) {
        Faculty facultyToBeUpdated = em.find(Faculty.class,faculty.getFacultyId());
        facultyToBeUpdated.setName(faculty.getName());
        return facultyToBeUpdated.getFacultyId();
    }

    //DELETE
    @Override
    public Long deleteFaculty(Faculty faculty) {        
        em.remove(faculty);
        Faculty f = em.find(Faculty.class, faculty.getFacultyId());
        em.remove(f);
        return faculty.getFacultyId();
    }
    
    
    
    
    
    
}
