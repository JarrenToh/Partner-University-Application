/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FAQ;
import entity.PU;
import entity.Student;
import error.NoResultException;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wayneonn
 */
@Local
public interface StudentSessionBeanLocal {

    public Long createStudent(Student student);

    public List<Student> retrieveAllStudents();

    public void updateStudent(Long studentId, String firstName, String lastName, String phoneNumber, String faculty, List<String> socialMedia, LocalDateTime lastActive, String email, String password);

    public void deleteStudent(Long studentId) throws NoResultException;

    public List<Student> searchStudentByLastName(String name);

    public List<Student> searchStudentByEmail(String email);

    public List<Student> searchStudentByPhone(String phone);

    public List<Student> searchStudentByFirstName(String name);
    
    public Student getStudent(Long studentId) throws NoResultException;

    public void updateStudent(Student s) throws NoResultException;

    public Student login(String username, String password);

    List<Student> retrieveStudentsByPU(PU pu);
    
}
