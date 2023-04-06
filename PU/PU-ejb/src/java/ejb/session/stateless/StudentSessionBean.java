/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PU;
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
public class StudentSessionBean implements StudentSessionBeanLocal {

    @PersistenceContext(unitName = "PU-ejbPU")
    private EntityManager em;

    @Override
    public Long createStudent(Student student) {
        em.persist(student);
        em.flush();
        
        return student.getStudentId();
    }

    @Override
    public Student getStudent(Long studentId) throws NoResultException {
        Student student = em.find(Student.class, studentId);
        if (student != null) {
            return student;
        } else {
            throw new NoResultException("Not found");
        }

//        return student;
    }

    @Override
    public List<Student> retrieveAllStudents() {
        Query query = em.createQuery("SELECT s FROM Student s");
        List<Student> students = query.getResultList();
        return students;
    }

    @Override
    public List<Student> retrieveStudentsByPU(PU pu) {
        Query q = em.createQuery("SELECT DISTINCT s FROM Student s, PU pu WHERE pu MEMBER OF s.puEnrolled AND pu.puId =:id");
        q.setParameter("id", pu.getPuId());
        return q.getResultList();
    }

    @Override
    public List<Student> searchStudentByFirstName(String name) {
        Query q;
        if (name != null) {
            q = em.createQuery("SELECT s FROM Student s WHERE "
                    + "LOWER(s.firstName) LIKE :name");
            q.setParameter("name", "%" + name.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT s FROM Customer s");
        }

        return q.getResultList();
    } //end searchCustomers

    @Override
    public List<Student> searchStudentByLastName(String name) {
        Query q;
        if (name != null) {
            q = em.createQuery("SELECT s FROM Student s WHERE "
                    + "LOWER(s.lastName) LIKE :name");
            q.setParameter("name", "%" + name.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT s FROM Customer s");
        }

        return q.getResultList();
    } //end searchCustomers

    @Override
    public List<Student> searchStudentByEmail(String email) {
        Query q;
        if (email != null) {
            q = em.createQuery("SELECT s FROM Student s WHERE "
                    + "LOWER(s.email) LIKE :name");
            q.setParameter("name", "%" + email.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT s FROM Customer s");
        }

        return q.getResultList();
    } //end searchCustomers

    @Override
    public List<Student> searchStudentByPhone(String phone) {
        Query q;
        if (phone != null) {
            q = em.createQuery("SELECT s FROM Student s WHERE "
                    + "LOWER(s.phoneNumber) LIKE :name");
            q.setParameter("name", "%" + phone.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT s FROM Customer s");
        }

        return q.getResultList();
    } //end searchCustomers

    @Override
    public void updateStudent(Long studentId, String firstName, String lastName, String phoneNumber, String faculty, List<String> socialMedia, LocalDateTime lastActive, String email, String password) {
        try {
            Student student = getStudent(studentId);

            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setPhoneNumber(phoneNumber);
            student.setFaculty(faculty);
            student.setSocialMedia(socialMedia);
            student.setLastActive(lastActive);
            student.setEmail(email);
            student.setPassword(password);
        } catch (NoResultException ex) {
            Logger.getLogger(StudentSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateStudent(Student s) throws NoResultException {
        Student oldS = getStudent(s.getStudentId());

        oldS.setFirstName(s.getFirstName());
        oldS.setLastName(s.getLastName());
        oldS.setPhoneNumber(s.getPhoneNumber());
        oldS.setFaculty(s.getFaculty());
        oldS.setSocialMedia(s.getSocialMedia());
        oldS.setLastActive(s.getLastActive());
        oldS.setEmail(s.getEmail());
        oldS.setPassword(s.getPassword());

    } //end updateCustomer

    @Override
    public void deleteStudent(Long studentId) throws NoResultException {
        try {
            Student studentToRemove = getStudent(studentId);
            em.remove(studentToRemove);
        } catch (NoResultException ex) {
            Logger.getLogger(StudentSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Student login(String username, String password) {
        Query query = em.createQuery("SELECT n FROM Student n WHERE n.email = :username AND n.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);

        return (Student) query.getSingleResult();
    }

}
