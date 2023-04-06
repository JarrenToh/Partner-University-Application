/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Country;
import entity.PU;
import entity.Region;
import entity.Student;
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
    public Long createNewPu(PU newPu, Long countryId) {
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
        Query query = em.createQuery("SELECT p FROM PU p WHERE LOWER(p.name) = :name");
        query.setParameter("name", name.toLowerCase().trim());

        return (PU) query.getSingleResult();
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
