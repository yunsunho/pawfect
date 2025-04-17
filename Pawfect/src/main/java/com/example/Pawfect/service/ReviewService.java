package com.example.Pawfect.service;

import java.util.List;
import java.util.Map;

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
    public int saveReview(int contentId, String userId, String reviewContent, int reviewRating,
        String title, int contentTypeId, String firstimage,
        String addr1, float mapX, float mapY, String areaCode, String sigunguCode) {

		ReviewDto review = new ReviewDto();
		review.setContentId(contentId);
		review.setUserId(userId);
		review.setReviewContent(reviewContent);
		review.setReviewRating(reviewRating);
		review.setTitle(title);
		review.setContentTypeId(contentTypeId);
		review.setFirstimage(firstimage);
		review.setAddr1(addr1);
		review.setMapX(mapX);
		review.setMapY(mapY);
		review.setAreaCode(areaCode);
		review.setSigunguCode(sigunguCode);
		
		reviewMapper.insertReview(review);
		return review.getReviewId();
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
    
 // 페이징된 리뷰 리스트 불러오기
    public List<ReviewDto> getPagedReviewsWithUser(int contentId, int offset, int limit) {
        List<ReviewDto> reviews = reviewMapper.selectPagedReviewsWithUser(contentId, offset, limit);
        for (ReviewDto review : reviews) {
            List<String> images = reviewMapper.selectReviewImagesByReviewId(review.getReviewId());
            review.setReviewImages(images);
        }
        return reviews;
    }

    // 총 리뷰 수 가져오기
    public int getTotalReviewCount(int contentId) {
        return reviewMapper.countReviewsByContentId(contentId);
    }
    
    public String getReviewOwner(int reviewId) {
        return reviewMapper.findUserIdByReviewId(reviewId);
    }

    public void deleteReviewById(int reviewId) {
        reviewMapper.deleteReview(reviewId);
    }

    public double getAverageRating(int contentId) {
        return reviewMapper.getAverageRating(contentId);
    }
    
    public List<Map<String, Object>> getContentIdsSortedByReviewCountAndType(int contentTypeId) {
        return reviewMapper.selectContentIdsSortedByReviewCountAndType(contentTypeId);
    }

    public List<Map<String, Object>> getContentIdsSortedByAvgRatingAndType(int contentTypeId) {
        return reviewMapper.selectContentIdsSortedByAvgRatingAndType(contentTypeId);
    }

    public List<Map<String, Object>> getContentIdsSortedByReviewCountAndArea(String areaCode, String sigunguCode) {
        return reviewMapper.selectContentIdsSortedByReviewCountAndArea(areaCode, sigunguCode);
    }
    
    public List<Map<String, Object>> getContentIdsSortedByAvgRatingAndArea(String areaCode, String sigunguCode) {
        return reviewMapper.selectContentIdsSortedByAvgRatingAndArea(areaCode, sigunguCode);
    }




}





