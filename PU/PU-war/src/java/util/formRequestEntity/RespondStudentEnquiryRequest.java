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
public class RespondStudentEnquiryRequest {
    
    private int status;
    private String response;
    
    public RespondStudentEnquiryRequest() {
        
    }
    
    public RespondStudentEnquiryRequest(int status, String response) {
        this.status = status;
        this.response = response;
    }

    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
    
}
