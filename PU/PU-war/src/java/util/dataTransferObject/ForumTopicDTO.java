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
public class ForumTopicDTO {
    private Long topicId;
    private String topicName;
    private Long puId;
    private boolean isInappropriate;

    public ForumTopicDTO(Long topicId, String topicName, Long puId, boolean isInappropriate) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.puId = puId;
        this.isInappropriate = isInappropriate;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Long getPuId() {
        return puId;
    }

    public void setPuId(Long puId) {
        this.puId = puId;
    }

    public boolean isIsInappropriate() {
        return isInappropriate;
    }

    public void setIsInappropriate(boolean isInappropriate) {
        this.isInappropriate = isInappropriate;
    }
    
    
}
