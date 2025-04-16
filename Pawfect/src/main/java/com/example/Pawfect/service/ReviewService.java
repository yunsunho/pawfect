package com.example.Pawfect.service;

import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.Pawfect.auth.*;
import com.example.Pawfect.dto.ReviewDto;
import com.example.Pawfect.dto.UserDto;
import com.example.Pawfect.mapper.ReviewMapper;

@Service
public class ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    // 로그인된 사용자 정보 가져오기
    public UserDto getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // 로그인된 사용자가 있을 때만 처리
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUser();  // 사용자 정보를 반환
        }
        
        return null;  // 로그인되지 않은 경우
    }

    public List<ReviewDto> getReviewsByContentId(int contentId) {
        return reviewMapper.selectReviewsWithUserByContentId(contentId);
    }

    // 리뷰와 이미지 저장
    public int saveReview(int contentId, String userId, String reviewContent, int reviewRating, String title, int contentTypeId) {
        ReviewDto review = new ReviewDto();
        review.setContentId(contentId);
        review.setUserId(userId);
        review.setTitle(title);
        review.setReviewContent(reviewContent);
        review.setReviewRating(reviewRating);
        review.setContentTypeId(contentTypeId);
        reviewMapper.insertReview(review); // INSERT + reviewId 세팅됨
        return review.getReviewId();       // 여기서 제대로 된 값 나옴
    }

    
    // 리뷰 이미지 저장
    public void saveReviewImageWithOrder(int reviewId, String imagePath, int imageOrder) {
        reviewMapper.insertReviewImageWithOrder(reviewId, imagePath, imageOrder);  // 순서와 함께 이미지 저장
    }
    
    // contentId에 해당하는 리뷰 이미지를 순서대로 불러오기
    public List<String> getReviewImagesByOrder(int contentId) {
        return reviewMapper.selectReviewImagesByOrder(contentId);  // 순서대로 이미지를 불러오는 쿼리 호출
    }
    
    public List<ReviewDto> getFullReviewsByContentId(int contentId) {
        List<ReviewDto> reviews = reviewMapper.selectReviewsWithUserByContentId(contentId);
        for (ReviewDto review : reviews) {
            List<String> images = reviewMapper.selectReviewImagesByReviewId(review.getReviewId());
            review.setReviewImages(images);
        }
        return reviews;
    }
}





