/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Faculty;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jarrentoh
 */
@Local
public interface FacultySessionBeanLocal {

    Long createFaculty(Faculty newFaculty);

    List<Faculty> retrieveAllFaculty();

    Faculty retrieveFacultyById(Long facultyId);

    List<Faculty> searchFacultiesByName(String facultyName);

    Long updateFaculty(Faculty faculty);

    Long deleteFaculty(Faculty faculty);
    
}
