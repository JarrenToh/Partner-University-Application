/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author muhdm
 */
@Entity
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long regionId;
    
    @Column (nullable = false, unique = true)
    @NotNull
    private String name;
    
    //relationship attributes
    @OneToMany (mappedBy = "region")
    private List<Country> countries;

    
    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }
    
    public Region(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
    
    public void addCountries(Country country) {
        this.countries.add(country);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (regionId != null ? regionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the regionId fields are not set
        if (!(object instanceof Region)) {
            return false;
        }
        Region other = (Region) object;
        if ((this.regionId == null && other.regionId != null) || (this.regionId != null && !this.regionId.equals(other.regionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Region[ id=" + regionId + " ]";
    }
    
}
