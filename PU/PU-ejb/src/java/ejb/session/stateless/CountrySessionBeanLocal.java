/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Country;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author muhdm
 */
@Local
public interface CountrySessionBeanLocal {

    public Long createNewCountry(Country newCountry);

    public List<Country> retrieveAllCountries();

    public Country retrieveCountryById(Long countryId);

    public Country retrieveCountryByName(String name);

    public Long createNewCountry(Country newCountry, Long RegionId);
    
}
