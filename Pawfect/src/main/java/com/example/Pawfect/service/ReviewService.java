package com.example.Pawfect.service;

import java.util.List;

import com.example.Pawfect.dto.ReviewDto;
import com.example.Pawfect.dto.ReviewImagesDto;

public interface ReviewService {
    int saveReview(ReviewDto reviewDTO);  // 리뷰 저장
    void saveReviewImages(int reviewId, List<ReviewImagesDto> images);  // 이미지 저장
}
