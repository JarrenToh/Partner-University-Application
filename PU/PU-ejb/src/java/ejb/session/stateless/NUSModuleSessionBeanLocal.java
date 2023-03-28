/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Faculty;
import entity.NUSModule;
import entity.PUModule;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jarrentoh
 */
@Local
public interface NUSModuleSessionBeanLocal {

    Long createNUSModule(NUSModule nusModule, Long facultyId);

    List<NUSModule> retrieveAllNUSModule();

    NUSModule retrieveNUSModuleByModuleId(Long moduleId);

    List<NUSModule> searchNUSModuleByCode(String code);

    Long updateNUSModule(NUSModule module);

    Long deleteNUSModule(NUSModule module);

    List<NUSModule> retrieveNUSModuleByFaculty(Faculty faculty);

    List<NUSModule> searchNUSModuleByFaculty(Faculty faculty);

    List<NUSModule> searchNUSModuleByPUModule(PUModule puModule);
    
}
