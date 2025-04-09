package com.example.Pawfect.dto;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private int reviewId;
    private int contentId;
    private String userId;
    private int reviewRating;
    private String reviewContent;
    private Timestamp reviewRegdate;
}
