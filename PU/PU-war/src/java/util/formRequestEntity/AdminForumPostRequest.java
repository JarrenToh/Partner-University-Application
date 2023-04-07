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
public class AdminForumPostRequest {
    private String title;
    private String message;
    private Integer noOfLikes;
    private Integer noOfDislikes;
    private Boolean isInappropriate;
    
    public AdminForumPostRequest() {
        
    }

    public AdminForumPostRequest(String title, String message, Integer noOfLikes, Integer noOfDislikes, Boolean isInappropriate) {
        this.title = title;
        this.message = message;
        this.noOfLikes = noOfLikes;
        this.noOfDislikes = noOfDislikes;
        this.isInappropriate = isInappropriate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getNoOfLikes() {
        return noOfLikes;
    }

    public void setNoOfLikes(Integer noOfLikes) {
        this.noOfLikes = noOfLikes;
    }

    public Integer getNoOfDislikes() {
        return noOfDislikes;
    }

    public void setNoOfDislikes(Integer noOfDislikes) {
        this.noOfDislikes = noOfDislikes;
    }

    public Boolean getIsInappropriate() {
        return isInappropriate;
    }

    public void setIsInappropriate(Boolean isInappropriate) {
        this.isInappropriate = isInappropriate;
    }
    
    
}
