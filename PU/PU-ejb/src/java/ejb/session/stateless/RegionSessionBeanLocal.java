/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Region;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author muhdm
 */
@Local
public interface RegionSessionBeanLocal {

    public Long createNewRegion(Region newRegion);

    public List<Region> retrieveAllRegions();

    public Region retrieveRegionById(Long regionId);

    public Region retrieveRegionByName(String name);
    
}
