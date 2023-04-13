/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PUModule;
import error.NoResultException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wayneonn
 */
@Local
public interface PUModuleSessionBeanLocal {
    
    public void createModuleForPU(String puName, PUModule module);

    public Long createPUModule(PUModule module, Long puId);

    public List<PUModule> retrieveAllPUModules();

    public void updatePUModule(Long moduleId, String code, String description);
    
    public void deletePUModuleFromPU(Long moduleId, String puName) throws NoResultException;

    public void deletePUModule(Long moduleId) throws NoResultException;

    public PUModule getPUModule(Long moduleId) throws NoResultException;
    
    public void updatePUModule(PUModule p) throws NoResultException;

    public List<PUModule> searchPUModuleByCode(String code);

    public List<PUModule> searchPUModuleByDescription(String description);
    
    public PUModule searchPUModuleByCodeAndPUName(String code, String puName);

    void associatePUModuleNUSModule(Long puModId, Long nusModId);

    public void associatePUModuleStudent(Long puModId, Long studentId);
    
}
