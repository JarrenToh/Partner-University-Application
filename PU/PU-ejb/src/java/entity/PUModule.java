/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author wayneonn
 */
@Entity
public class PUModule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moduleId;
    
    private String name;

    private String code;

    private String description;
    
    @OneToMany(mappedBy = "module")
    private List<PUModuleReview> moduleReviews;


    @ManyToMany
    @JoinColumn(nullable = true)
    private List<NUSModule> mappableModules;

    @ManyToOne
    private PU pu;

    public PUModule() {
        mappableModules = new ArrayList<>();
    }

    public PUModule(String name, String code, String description) {
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
        if (!(object instanceof PUModule)) {
            return false;
        }
        PUModule other = (PUModule) object;
        if ((this.moduleId == null && other.moduleId != null) || (this.moduleId != null && !this.moduleId.equals(other.moduleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PUModule[ id=" + moduleId + " ]";
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
     * @return the moduleReviews
     */
    public List<PUModuleReview> getModuleReviews() {
        return moduleReviews;
    }

    /**
     * @param moduleReviews the moduleReviews to set
     */
    public void setModuleReviews(List<PUModuleReview> moduleReviews) {
        this.moduleReviews = moduleReviews;
    }

    /**
     * @return the mappableModules
     */
    public List<NUSModule> getMappableModules() {
        return mappableModules;
    }

    /**
     * @param mappableModules the mappableModules to set
     */
    public void setMappableModules(List<NUSModule> mappableModules) {
        this.mappableModules = mappableModules;
    }

    /**
     * @return the pu
     */
    @JsonbTransient
    public PU getPu() {
        return pu;
    }

    /**
     * @param pu the pu to set
     */
    public void setPu(PU pu) {
        this.pu = pu;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
