/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.formRequestEntity;

/**
 *
 * @author kathleen
 */
public class ForumTopicRequest {

    private String topicName;
    
    private Long puId;
    
    /**
     * @return the topicName
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * @param topicName the topicName to set
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    } 
    
    /**
     * @return the puId
     */
    public Long getPuId() {
        return puId;
    }

    /**
     * @param puId the puId to set
     */
    public void setPuId(Long puId) {
        this.puId = puId;
    }   
    
}
