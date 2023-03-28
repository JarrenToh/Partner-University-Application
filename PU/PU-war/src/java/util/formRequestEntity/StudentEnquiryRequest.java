/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.formRequestEntity;

/**
 *
 * @author wjahoward
 */
public class StudentEnquiryRequest {
    private Long enquiryId;
    private String title;
    private String content;

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
    
    
}
