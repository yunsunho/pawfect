package com.example.Pawfect.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.Pawfect.dto.ReviewDto;
import com.example.Pawfect.dto.ReviewImagesDto;

@Mapper
public interface ReviewMapper {
    void insertReview(ReviewDto review);
    void insertReviewImage(ReviewImagesDto image);
}

