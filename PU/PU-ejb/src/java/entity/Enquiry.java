/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import util.enumeration.EnquiryStatusEnum;

/**
 *
 * @author wjahoward
 */
@Entity
public class Enquiry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enquiryId;

    @Column
    private String title;
    
    @Column
    private String content;
    
    @Column
    private String response;

    @Column
    private LocalDateTime enquiryDate;
    
    @Column
    private LocalDateTime responseStatusDate;

    @Column
    private EnquiryStatusEnum status;
    
    @ManyToOne(optional = true, fetch = FetchType.EAGER) // only once when the enquiry is taken up by the admin, then need to update this
    @JoinColumn(nullable = true)
    private NUSchangeAdmin nuschangeAdmin;
    
    // TODO: Need to change to false for the optional and nullable
    @ManyToOne(optional = true, fetch = FetchType.EAGER) // student can also send in their enquiries
    @JoinColumn(nullable = true)
    @JsonbTransient
    private Student student;
    
    public Enquiry() {

    }
    
    public Enquiry(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public LocalDateTime getEnquiryDate() {
        return enquiryDate;
    }

    public void setEnquiryDate(LocalDateTime enquiryDate) {
        this.enquiryDate = enquiryDate;
    }

    public EnquiryStatusEnum getStatus() {
        return status;
    }

    public void setStatus(EnquiryStatusEnum status) {
        this.status = status;
    }

    public Long getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(Long enquiryId) {
        this.enquiryId = enquiryId;
    }

    public NUSchangeAdmin getNUSchangeAdmin() {
        return nuschangeAdmin;
    }

    public void setNUSchangeAdmin(NUSchangeAdmin nuschangeAdmin) {
        this.nuschangeAdmin = nuschangeAdmin;
    }

    public LocalDateTime getResponseStatusDate() {
        return responseStatusDate;
    }

    public void setResponseStatusDate(LocalDateTime responseStatusDate) {
        this.responseStatusDate = responseStatusDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (enquiryId != null ? enquiryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the enquiryId fields are not set
        if (!(object instanceof Enquiry)) {
            return false;
        }
        Enquiry other = (Enquiry) object;
        if ((this.enquiryId == null && other.enquiryId != null) || (this.enquiryId != null && !this.enquiryId.equals(other.enquiryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Enquiry[ id=" + enquiryId + " ]";
    }

    /**
     * @return the student
     */
    @JsonbTransient
    public Student getStudent() {
        return student;
    }

    /**
     * @param student the student to set
     */
    public void setStudent(Student student) {
        this.student = student;
    }

}
