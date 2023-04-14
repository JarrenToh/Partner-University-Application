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
public class AdminForumTopicRequest {
    private String topicName;
    private Boolean isInappropriate;
    private Long puId;
    
    public AdminForumTopicRequest() {
        
    }

    public AdminForumTopicRequest(String topicName, Boolean isInappropriate, Long puId) {
        this.topicName = topicName;
        this.isInappropriate = isInappropriate;
        this.puId = puId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Boolean getIsInappropriate() {
        return isInappropriate;
    }

    public void setIsInappropriate(Boolean isInappropriate) {
        this.isInappropriate = isInappropriate;
    }

    public Long getPuId() {
        return puId;
    }

    public void setPuId(Long puId) {
        this.puId = puId;
    }
    
    
}
