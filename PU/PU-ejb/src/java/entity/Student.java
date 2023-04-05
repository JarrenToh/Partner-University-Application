/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


/**
 *
 * @author wayneonn
 */
@Entity
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String faculty;

    private List<String> socialMedia;

    private LocalDateTime lastActive;

    private String email;

    private String password;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
    private List<Enquiry> enquiries;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<PU> likedPUs;
    
    @OneToMany(mappedBy = "students")
    private List<PUModuleReview> moduleReviews;
    
    @ManyToOne
    @JoinColumn(nullable = true)
    private PU puEnrolled;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
    private List<ForumPost> posts;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
    private List<ForumTopic> topics;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
    private List<ForumComment> comments;

    @OneToMany
    private List<PUModule> modulesTaken;

    @OneToOne(mappedBy = "student")
    private PUReview puReview;

    public Student() {
        this.socialMedia = new ArrayList<>();
        this.enquiries = new ArrayList<>();
        this.likedPUs = new ArrayList<>();
        this.moduleReviews = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.topics = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.modulesTaken = new ArrayList<>();
    }

    public Student(String firstName, String lastName, String phoneNumber, String email, String password, String faculty) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.faculty = faculty;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getStudentId() != null ? getStudentId().hashCode() : 0);
        return hash;
    }

    public Student(String firstName, String lastName, String phoneNumber, String email, String password) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the studentId fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.getStudentId() == null && other.getStudentId() != null) || (this.getStudentId() != null && !this.studentId.equals(other.studentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Student[ id=" + getStudentId() + " ]";
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the faculty
     */
    public String getFaculty() {
        return faculty;
    }

    /**
     * @param faculty the faculty to set
     */
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    /**
     * @return the socialMedia
     */
    public List<String> getSocialMedia() {
        return socialMedia;
    }

    /**
     * @param socialMedia the socialMedia to set
     */
    public void setSocialMedia(List<String> socialMedia) {
        this.socialMedia = socialMedia;
    }

    /**
     * @return the lastActive
     */
    public LocalDateTime getLastActive() {
        return lastActive;
    }

    /**
     * @param lastActive the lastActive to set
     */
    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the enquiries
     */
    public List<Enquiry> getEnquiries() {
        return enquiries;
    }

    /**
     * @param enquiries the enquiries to set
     */
    public void setEnquiries(List<Enquiry> enquiries) {
        this.enquiries = enquiries;
    }

    /**
     * @return the puEnrolled
     */
    @JsonbTransient
    public PU getPuEnrolled() {
        return puEnrolled;
    }

    /**
     * @param puEnrolled the puEnrolled to set
     */
    public void setPuEnrolled(PU puEnrolled) {
        this.puEnrolled = puEnrolled;
    }

    /**
     * @return the likedPUs
     */
    public List<PU> getLikedPUs() {
        return likedPUs;
    }

    /**
     * @param likedPUs the likedPUs to set
     */
    public void setLikedPUs(List<PU> likedPUs) {
        this.likedPUs = likedPUs;
    }

    /**
     * @return the modulesTaken
     */
    public List<PUModule> getModulesTaken() {
        return modulesTaken;
    }

    /**
     * @param modulesTaken the modulesTaken to set
     */
    public void setModulesTaken(List<PUModule> modulesTaken) {
        this.modulesTaken = modulesTaken;
    }

    /**
     * @return the posts
     */
    public List<ForumPost> getPosts() {
        return posts;
    }

    /**
     * @param posts the posts to set
     */
    public void setPosts(List<ForumPost> posts) {
        this.posts = posts;
    }

    /**
     * @return the topics
     */
    public List<ForumTopic> getTopics() {
        return topics;
    }

    /**
     * @param topics the topics to set
     */
    public void setTopics(List<ForumTopic> topics) {
        this.topics = topics;
    }

    /**
     * @return the comments
     */
    public List<ForumComment> getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(List<ForumComment> comments) {
        this.comments = comments;
    }

    /**
     * @return the pUReviews
     */
    public PUReview getPuReview() {
        return puReview;
    }

    /**
     * @param pUReviews the pUReviews to set
     */
    public void setPuReview(PUReview puReview) {
        this.puReview = puReview;
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
}
