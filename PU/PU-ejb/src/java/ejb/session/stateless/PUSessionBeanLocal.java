/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PU;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author muhdm
 */
@Local
public interface PUSessionBeanLocal {

    public Long createNewPu(PU newPu, Long countryId);

    public Long createNewPu(PU newPu);

    public List<PU> retrieveAllPus();

    public PU retrievePuById(Long puId);

    public PU retrievePuByName(String name);

    List<Object> getMappableModulesGroupedByFaculty(String puName);

    Long enrollStudent(Long puId, Long studentId);
    
}
