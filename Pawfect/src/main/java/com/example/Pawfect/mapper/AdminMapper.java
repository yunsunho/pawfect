package com.example.Pawfect.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.Pawfect.dto.CommentDto;
import com.example.Pawfect.dto.InquiryDto;
import com.example.Pawfect.dto.PostDto;
import com.example.Pawfect.dto.ReviewDto;
import com.example.Pawfect.dto.UserDto;

@Mapper
public interface AdminMapper {
	public int getUserCount(Map<String, Object> map);
	public List<UserDto> getAllUsers(Map<String, Object> map);
	public int updateUserStatus(UserDto dto);
	
	public int getPostCount(Map<String, Object> map);
	public List<PostDto> getAllPosts(Map<String, Object> map);
	
	public int getCommentCount(Map<String, Object> map);
	public List<CommentDto> getAllComments(Map<String, Object> map);
	public int deleteComment(int commentId);
	
	public int getReviewCount(Map<String, Object> map);
	public List<ReviewDto> getAllReviews(Map<String, Object> map);
	public int deleteReview(int reviewId);
	
	public int getInquiryCount(Map<String, Object> map);
	public List<InquiryDto> getAllInquiries(Map<String, Object> map);
	
	public int getHandledInquiryCount(Map<String, Object> map);
	public int getUnhandledInquiryCount(Map<String, Object> map);
	public InquiryDto getInquiryById(int inquiryId);
	public int replyToInquiry(InquiryDto dto);
	
	public int getTotalReviewCount();
	public int getTotalActiveUserCount();
	public int getTotalBannedUserCount();
	public int getTotalWithdrawnUserCount();
	public List<Map<String, Object>> getUserRegistrationCountPerDay();
	public List<Map<String, Object>> getPostCountByDate(); 
	public List<Map<String, Object>> getReviewCountByDate(); 
}
