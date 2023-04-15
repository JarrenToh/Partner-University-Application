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
public class AdminForumTopicDTO {
    private Long topicId;
    private String topicName;
    private Long puId;
    private boolean isInappropriate;
    private Long adminId;

    public AdminForumTopicDTO(Long topicId, String topicName, Long puId, boolean isInappropriate, Long adminId) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.puId = puId;
        this.isInappropriate = isInappropriate;
        this.adminId = adminId;
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

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
}
