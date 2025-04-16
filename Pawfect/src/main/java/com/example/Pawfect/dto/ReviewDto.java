package com.example.Pawfect.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor 
public class ReviewDto {
	
	private String userImage;            // 프로필 사진
	private String userNickname;         // 닉네임 (앞 3글자 + *** 처리 필요)
	
	private int reviewId;
    private int contentId;
    private int contentTypeId;
    private String title;
    private String userId;
    private String imgpath;
    private String reviewContent;
    private int reviewRating;
    private Timestamp reviewRegdate;
    
    private List<String> reviewImages;
    private String formattedDate;
    
    // (int, String, String, int) 매개변수를 받는 생성자 추가
    public ReviewDto(int contentId, String userId, String reviewContent, int reviewRating) {
        this.contentId = contentId;
        this.userId = userId;
        this.reviewContent = reviewContent;
        this.reviewRating = reviewRating;
    }
}
