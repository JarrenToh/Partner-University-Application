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
public class AdminProfileRequest {
    private String updateString;
    
    public AdminProfileRequest() {
        
    }
    
    public AdminProfileRequest(String updateString) {
        this.updateString = updateString;
    }
    
    public String getUpdateString() {
        return this.updateString;
    }
    
    public void setUpdateString(String updateString) {
        this.updateString = updateString;
    }
}
