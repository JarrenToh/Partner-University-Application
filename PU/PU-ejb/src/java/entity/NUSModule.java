/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author jarrentoh
 */
@Entity
public class NUSModule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moduleId;

    private String code;
    private String description;

    //Add in mapping to PUModule   
    @ManyToMany(mappedBy = "mappableModules", fetch = FetchType.EAGER)
    private List<PUModule> puModules;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Faculty faculty;

    public NUSModule() {

        puModules = new ArrayList<>();
    }

    public NUSModule(String code, String description) {
        this();
        this.code = code;
        this.description = description;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (moduleId != null ? moduleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the moduleId fields are not set
        if (!(object instanceof NUSModule)) {
            return false;
        }
        NUSModule other = (NUSModule) object;
        if ((this.moduleId == null && other.moduleId != null) || (this.moduleId != null && !this.moduleId.equals(other.moduleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.NUSModule[ id=" + moduleId + " ]";
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the puModules
     */
    public List<PUModule> getPuModules() {
        return puModules;
    }

    /**
     * @param puModules the puModules to set
     */
    public void setPuModules(List<PUModule> puModules) {
        this.puModules = puModules;
    }

    /**
     * @return the faculty
     */
    public Faculty getFaculty() {
        return faculty;
    }

    /**
     * @param faculty the faculty to set
     */
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

}
