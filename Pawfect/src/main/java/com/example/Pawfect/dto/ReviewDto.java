package com.example.Pawfect.dto;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor 
public class ReviewDto {
	
	private int reviewId;
    private int contentId;
    private String userId;
    private String reviewContent;
    private int reviewRating;
    private Timestamp reviewRegdate;
    
    // (int, String, String, int) 매개변수를 받는 생성자 추가
    public ReviewDto(int contentId, String userId, String reviewContent, int reviewRating) {
        this.contentId = contentId;
        this.userId = userId;
        this.reviewContent = reviewContent;
        this.reviewRating = reviewRating;
    }
}
