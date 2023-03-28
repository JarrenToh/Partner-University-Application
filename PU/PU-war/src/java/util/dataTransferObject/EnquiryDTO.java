/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dataTransferObject;

import java.time.LocalDateTime;
import util.enumeration.EnquiryStatusEnum;

/**
 *
 * @author wjahoward
 */
public class EnquiryDTO {
    private Long enquiryId;
    private String title;
    private String content;
    private String response;
    private EnquiryStatusEnum status;
    private LocalDateTime enquiryDate;
    private LocalDateTime responseStatusDate;

    public Long getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(Long enquiryId) {
        this.enquiryId = enquiryId;
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

    public EnquiryStatusEnum getStatus() {
        return status;
    }

    public void setStatus(EnquiryStatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getEnquiryDate() {
        return enquiryDate;
    }

    public void setEnquiryDate(LocalDateTime enquiryDate) {
        this.enquiryDate = enquiryDate;
    }

    public LocalDateTime getResponseStatusDate() {
        return responseStatusDate;
    }

    public void setResponseStatusDate(LocalDateTime responseStatusDate) {
        this.responseStatusDate = responseStatusDate;
    }
    
    
}
