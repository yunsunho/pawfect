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
}