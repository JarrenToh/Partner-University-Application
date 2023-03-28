/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import util.enumeration.UserGroupEnum;

/**
 *
 * @author wjahoward
 */
// parent
@Entity
public class NUSchangeAdmin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long adminId;
    
    @Column
    private String name;
    
    @Column
    private String username;
    
    @Column
    private String password;
    
    @Column
    private UserGroupEnum userGroupEnum;
    
    @OneToMany(fetch = FetchType.LAZY) // this is for UserSupportAdmin
    private List<FAQ> faqs;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "nuschangeAdmin") // this is for UserSupportAdmin
    @JsonbTransient
    private List<Enquiry> enquiries;
    
    @OneToMany(fetch = FetchType.EAGER) // this is for SystemSupportAdmin
    @JsonbTransient
    private List<ForumTopic> forumTopics;
    
    public NUSchangeAdmin() {
        
    }
        
    public NUSchangeAdmin(String name, String username, String password, UserGroupEnum userGroupEnum) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.userGroupEnum = userGroupEnum;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adminId != null ? adminId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the adminId fields are not set
        if (!(object instanceof NUSchangeAdmin)) {
            return false;
        }
        NUSchangeAdmin other = (NUSchangeAdmin) object;
        if ((this.adminId == null && other.adminId != null) || (this.adminId != null && !this.adminId.equals(other.adminId))) {
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserGroupEnum getUserGroupEnum() {
        return userGroupEnum;
    }

    public void setUserGroupEnum(UserGroupEnum userGroupEnum) {
        this.userGroupEnum = userGroupEnum;
    }

    public List<FAQ> getFaqs() {
        return faqs;
    }

    public void setFaqs(List<FAQ> faqs) {
        this.faqs = faqs;
    }

    public List<ForumTopic> getForumTopics() {
        return forumTopics;
    }

    public void setForumTopics(List<ForumTopic> forumTopics) {
        this.forumTopics = forumTopics;
    }

    public List<Enquiry> getEnquiries() {
        return enquiries;
    }

    public void setEnquiries(List<Enquiry> enquiries) {
        this.enquiries = enquiries;
    }
    
    @Override
    public String toString() {
        return "entity.NUSchangeAdmin[ id=" + adminId + " ]";
    }
    
}
