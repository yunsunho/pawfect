package com.example.Pawfect.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Pawfect.dto.ReviewDto;
import com.example.Pawfect.dto.ReviewImagesDto;
import com.example.Pawfect.mapper.ReviewMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;

    @Override
    public int saveReview(ReviewDto reviewDTO) {
        reviewMapper.insertReview(reviewDTO);
        return reviewDTO.getReviewId(); // AUTO_INCREMENT된 ID 반환
    }

    @Override
    public void saveReviewImages(int reviewId, List<ReviewImagesDto> images) {
        for (ReviewImagesDto image : images) {
            image.setReviewId(reviewId);
            reviewMapper.insertReviewImage(image);
        }
    }
}

