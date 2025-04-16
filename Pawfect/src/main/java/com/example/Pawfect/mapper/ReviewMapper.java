package com.example.Pawfect.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.Pawfect.dto.ReviewDto;

@Mapper  
public interface ReviewMapper {

    // contentId에 해당하는 리뷰 목록을 가져오는 메서드
	List<ReviewDto> selectReviewsWithUserByContentId(int contentId);

	void insertReview(ReviewDto review);
	
	int getLastInsertedReviewId();

	void insertReviewImageWithOrder(int reviewId, String imagePath, int imageOrder);

	List<String> selectReviewImagesByOrder(int contentId);
	
	List<String> selectReviewImagesByReviewId(int reviewId);

	int countReviewsByContentId(int contentId);

	List<ReviewDto> selectPagedReviewsWithUser(
		    @org.apache.ibatis.annotations.Param("contentId") int contentId,
		    @org.apache.ibatis.annotations.Param("offset") int offset,
		    @org.apache.ibatis.annotations.Param("limit") int limit
		);

}