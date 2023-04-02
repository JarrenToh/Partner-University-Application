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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jarrentoh
 */
@Entity
public class PU implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long puId;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false, columnDefinition = "VARCHAR(5000)")
    @NotNull
    private String description;

    @Column(nullable = false, columnDefinition = "VARCHAR(10000)")
    @NotNull
    private String images;

    //relationship attributes
    @ManyToOne
    @JoinColumn(nullable = false)
    private Country country;

    @OneToMany(mappedBy = "pu", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<PUModule> modules;

    @OneToMany(mappedBy = "pu", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<PUReview> puReviews;

    @OneToMany(mappedBy = "puEnrolled", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Student> students;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<ForumTopic> forumTopic;

    public PU() {

        modules = new ArrayList<>();
        puReviews = new ArrayList<>();
        students = new ArrayList<>();
        forumTopic = new ArrayList<>();
    }

    public PU(String name, String description, String images) {
        this();
        this.name = name;
        this.description = description;
        this.images = images;
    }

    public PU(String name, String description, String images, Country country) {
        this.name = name;
        this.description = description;
        this.images = images;
        this.country = country;
    }

    public Long getPuId() {
        return puId;
    }

    public void setPuId(Long puId) {
        this.puId = puId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImages() {
        return images;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getPuId() != null ? getPuId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the puId fields are not set
        if (!(object instanceof PU)) {
            return false;
        }
        PU other = (PU) object;
        if ((this.getPuId() == null && other.getPuId() != null) || (this.getPuId() != null && !this.puId.equals(other.puId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PU[ id=" + getPuId() + " ]";
    }

    /**
     * @return the modules
     */
    public List<PUModule> getModules() {
        return modules;
    }

    /**
     * @param modules the modules to set
     */
    public void setModules(List<PUModule> modules) {
        this.modules = modules;
    }

    /**
     * @return the puReviews
     */
    public List<PUReview> getPuReviews() {
        return puReviews;
    }

    /**
     * @param puReviews the puReviews to set
     */
    public void setPuReviews(List<PUReview> puReviews) {
        this.puReviews = puReviews;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param images the images to set
     */
    public void setImages(String images) {
        this.images = images;
    }

    /**
     * @return the students
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * @param students the students to set
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    /**
     * @return the forumTopic
     */
    public List<ForumTopic> getForumTopic() {
        return forumTopic;
    }

    /**
     * @param forumTopic the forumTopic to set
     */
    public void setForumTopic(List<ForumTopic> forumTopic) {
        this.forumTopic = forumTopic;
    }

}
