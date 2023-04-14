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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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

    private String countryName;
    private String regionName;
    private Double rating;

    //relationship attributes
    @ManyToOne
    private Country country;

    @OneToMany(mappedBy = "pu", cascade = {CascadeType.ALL, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PUModule> modules;

    @OneToMany(mappedBy = "pu", cascade = {CascadeType.ALL, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PUReview> puReviews;

    @OneToMany(mappedBy = "puEnrolled", cascade = {CascadeType.ALL, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Student> students;
    
    @OneToMany(mappedBy = "pu", cascade = {CascadeType.ALL, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ForumTopic> forumTopics;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL, CascadeType.REMOVE})
    @JsonbTransient
    private List<Student> studentsLiked;
    
    public PU() {
        students = new ArrayList<>();
        modules = new ArrayList<>();
        puReviews = new ArrayList<>();
        studentsLiked = new ArrayList<>();
    }

    public PU(String name, String description, String images) {
        this();
        this.name = name;
        this.description = description;
        this.images = images;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @JsonbTransient
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
   
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
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

    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName the countryName to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return the regionName
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * @param regionName the regionName to set
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    /**
     * @return the rating
     */
    public Double getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<Student> getStudentsLiked() {
        return studentsLiked;
    }

    public void setStudentsLiked(List<Student> studentsLiked) {
        this.studentsLiked = studentsLiked;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (puId != null ? puId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the puId fields are not set
        if (!(object instanceof PU)) {
            return false;
        }
        PU other = (PU) object;
        if ((this.puId == null && other.puId != null) || (this.puId != null && !this.puId.equals(other.puId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PU[ id=" + puId + " ]";
    }

    public List<ForumTopic> getForumTopics() {
        return forumTopics;
    }

    public void setForumTopics(List<ForumTopic> forumTopics) {
        this.forumTopics = forumTopics;
    }
}
