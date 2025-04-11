package com.example.Pawfect.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
    private int postId; 
    private String userId;
    private int postType;
    private String postTitle;
    private String postContent;
    private Timestamp postRegdate;
    private int postViewCount;
    
    private String userNickname; 
    private int likeCount;
    private String displayName;
    // comment count???
    
    public void generateDisplayName() {
        String maskedId = (userId != null && userId.length() >= 3)
            ? userId.substring(0, 3) + "***"
            : userId + "***";
        this.displayName = userNickname + " (" + maskedId + ")";
    }
}