/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dataTransferObject;

/**
 *
 * @author wjahoward
 */
public class ResponseObject {
    private String stringStatus;
    
    public ResponseObject(String stringStatus) {
        this.stringStatus = stringStatus;
    }

    public String getStringStatus() {
        return stringStatus;
    }

    public void setStringStatus(String stringStatus) {
        this.stringStatus = stringStatus;
    }
}
