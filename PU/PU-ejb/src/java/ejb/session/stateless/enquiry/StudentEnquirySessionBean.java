/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless.enquiry;

import entity.Enquiry;
import entity.Student;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import util.enumeration.EnquiryStatusEnum;

/**
 *
 * @author wjahoward
 */
@Stateless
public class StudentEnquirySessionBean implements StudentEnquirySessionBeanLocal {

    @PersistenceContext(unitName = "PU-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Enquiry createEnquiry(Enquiry enquiry, Long studentId) {
        Student student = em.find(Student.class, studentId);
        student.getEnquiries().add(enquiry);

        enquiry.setResponse("");
        enquiry.setEnquiryDate(LocalDateTime.now());
        enquiry.setStatus(EnquiryStatusEnum.PENDING);
        enquiry.setStudent(student);

        em.persist(enquiry);
        em.flush();

        return enquiry;
    }

    @Override
    public List<Enquiry> retrieveOwnEnquires(Long studentId) {
        Query query = em.createQuery("SELECT e FROM Enquiry e WHERE e.student.studentId = :studentId");
        query.setParameter("studentId", studentId);
        return query.getResultList();
    }

    @Override
    public void updateEnquiry(Long enquiryId, Long studentId, String title, String content) {
        Enquiry enquiry = retrieveSpecificEnquiry(enquiryId, studentId);

        if (enquiry == null) {
            throw new NotFoundException("User does not have enquiry with enquiryId of " + enquiryId);
        }

        enquiry.setTitle(title);
        enquiry.setContent(content);
        enquiry.setEnquiryDate(LocalDateTime.now());

    }

    @Override
    public void deleteEnquiry(Long enquiryId, Long studentId) {
        Enquiry deletedEnquiry = retrieveSpecificEnquiry(enquiryId, studentId);

        if (deletedEnquiry == null) {
            throw new NotFoundException("User does not have enquiry with enquiryId of " + enquiryId);
        }

        em.remove(deletedEnquiry);
    }

    // need to make sure the enquiry is from the original owner
    private Enquiry retrieveSpecificEnquiry(Long enquiryId, Long studentId) {
        Query query = em.createQuery("SELECT e FROM Enquiry e WHERE e.enquiryId = :enquiryId AND e.student.studentId = :studentId");
        query.setParameter("enquiryId", enquiryId);
        query.setParameter("studentId", studentId);

        Enquiry enquiry = (Enquiry) query.getSingleResult();

        return enquiry;
    }
}
