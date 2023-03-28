/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author wjahoward
 */
@Entity
public class FAQ implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long faqId;
    
    @Column
    private String question;
    
    @Column
    private String answer;
    
    @Column
    private LocalDateTime created;
    
    @Column
    private LocalDateTime lastEdit;
    
    @ManyToOne
    @JoinColumn(name = "created_by")
    private NUSchangeAdmin createdBy;
        
    public FAQ() {
        
    }
    
    public FAQ(String question, String answer) { // when creating
        this.question = question;
        this.answer = answer;
    }
    
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(LocalDateTime lastEdit) {
        this.lastEdit = lastEdit;
    }

    public Long getFaqId() {
        return faqId;
    }

    public void setFaqId(Long faqId) {
        this.faqId = faqId;
    }

    public NUSchangeAdmin getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(NUSchangeAdmin createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (faqId != null ? faqId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the faqId fields are not set
        if (!(object instanceof FAQ)) {
            return false;
        }
        FAQ other = (FAQ) object;
        if ((this.faqId == null && other.faqId != null) || (this.faqId != null && !this.faqId.equals(other.faqId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FAQ[ id=" + faqId + " ]";
    }
    
}
