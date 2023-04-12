/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Country;
import entity.PU;
import entity.Student;
import entity.PUModule;
import entity.PUReview;
import error.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @EJB
    private PUModuleSessionBeanLocal puModuleSessionBean;

    @Override
    public Long createNewPu(PU newPu) {
        em.persist(newPu);
        em.flush();

        return newPu.getPuId();
    }

    //with proper associations
    @Override
    public Long createNewPu(PU newPu, Long countryId) {
        Country country = countrySessionBean.retrieveCountryById(countryId);

        newPu.setCountry(country);
        country.addPu(newPu);

        em.persist(newPu);
        em.flush();
        return newPu.getPuId();
    }

    @Override
    public Long createNewPu(PU newPu, List<Long> moduleIds) {
        List<PUModule> puModules = new ArrayList<>();

        for (Long moduleId : moduleIds) {
            PUModule puModule = null;

            try {
                puModule = puModuleSessionBean.getPUModule(moduleId);
            } catch (NoResultException ex) {
                Logger.getLogger(PUSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            }

            puModules.add(puModule);
        }

        newPu.setModules(puModules);

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

        Query query = em.createQuery("SELECT p FROM PU p WHERE LOWER(p.name) = :name");
        query.setParameter("name", name.toLowerCase().trim());

        PU pu = (PU) query.getSingleResult();

        return pu;
    }

    @Override
    public List<Object> getMappableModulesGroupedByFaculty(String puName) {
        Query query = em.createQuery("SELECT m, m.faculty.name \n"
                + "FROM NUSModule m \n"
                + "JOIN m.puModules p \n"
                + "JOIN m.faculty f "
                + "WHERE p.pu.name = :puName "
                + "GROUP BY f, m");

        query.setParameter("puName", puName.toLowerCase().trim());
        return query.getResultList();
    }

    @Override
    public void updatePU(Long puId, String name, String description, String images) {
        PU pu = retrievePuById(puId);

        pu.setName(name);
        pu.setDescription(description);
        pu.setImages(images);
    }

    @Override
    public void updatePU(PU pu) {
        PU oldPu = retrievePuById(pu.getPuId());
       oldPu.setStudentsLiked(pu.getStudentsLiked());
    }
    
    @Override
    public void updatePUAdmin(Long puId, String name, String description, String images, Long countryId) {
        PU pu = retrievePuById(puId);
        
        pu.setName(name);
        pu.setDescription(description);
        pu.setImages(images);
        
        Country country = countrySessionBean.retrieveCountryById(countryId);
        pu.setCountry(country);
    }
    
    @Override
    public void deletePU(Long puId) {
        PU deletedPU = retrievePuById(puId);

        // retrieve all related modules and delete them
        List<PUModule> modulesToDelete = deletedPU.getModules();

        for (PUModule module : modulesToDelete) {
            em.remove(module);
        }

        List<PUReview> pureviews = deletedPU.getPuReviews();

        for (PUReview pureview : pureviews) {
            em.remove(pureview);
        }
        
        List<Student> students = deletedPU.getStudents();

        for (Student student : students) {
            em.remove(student);
        }

        em.remove(deletedPU);
    }

    @Override
    public Long enrollStudent(Long puId, Long studentId) {
        PU pu = em.find(PU.class, puId);
        Student student = em.find(Student.class, studentId);
        pu.getStudents().add(student);
        student.setPuEnrolled(pu);
        em.flush();

        return pu.getPuId();
    }

}
//    @Override
//    public List<Object> getMappableModulesGroupedByFaculty(String puName) {
//        Query query = em.createQuery("SELECT faculty \n"
//                + "FROM NUSModule n \n"
//                + "JOIN n.puModules pm \n"
//                + "JOIN n.faculty faculty \n"
//                + "WHERE LOWER(pm.pu.name) = :puName \n"
//                + "GROUP BY faculty");
//        query.setParameter("puName", puName.toLowerCase().trim());
//        return query.getResultList();
//    }
