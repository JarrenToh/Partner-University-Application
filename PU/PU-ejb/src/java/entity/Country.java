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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author muhdm
 */
@Entity
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;

    public Country() {
    }
    
    @Column (nullable = false, unique = true)
    @NotNull
    private String name;
    
    //relationship attributes
    @ManyToOne
    private Region region;
    
    @OneToMany (mappedBy = "country")
    private List<PU> pus;

    public Long getCountryId() {
        return countryId;
    }

    public Country(String name, Region region) {
        this.name = name;
        this.region = region;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Country() {
    }

    public List<PU> getPus() {
        return pus;
    }

    public void setPus(List<PU> pus) {
        this.pus = pus;
    }
    
    public void addPu(PU pu) {
        this.pus.add(pu);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (countryId != null ? countryId.hashCode() : 0);
        return hash;
    }

    public Country(String name, Region region) {
        this.name = name;
        this.region = region;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the countryId fields are not set
        if (!(object instanceof Country)) {
            return false;
        }
        Country other = (Country) object;
        if ((this.countryId == null && other.countryId != null) || (this.countryId != null && !this.countryId.equals(other.countryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Country[ id=" + countryId + " ]";
    }
    
}
