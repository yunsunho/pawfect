package com.example.Pawfect.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Pawfect.dto.CommentDto;
import com.example.Pawfect.dto.InquiryDto;
import com.example.Pawfect.dto.NotificationDto;
import com.example.Pawfect.dto.PostDto;
import com.example.Pawfect.dto.ReviewDto;
import com.example.Pawfect.dto.UserDto;
import com.example.Pawfect.mapper.AdminMapper;

@Service
public class AdminService {
	@Autowired
	private AdminMapper adminMapper;

	@Autowired
	private NotificationService notificationService;

	public int getUserCount(Map<String, Object> map) {
		return adminMapper.getUserCount(map);
	}

	public List<UserDto> getAllUsers(Map<String, Object> map) {
		return adminMapper.getAllUsers(map);
	}

	public int updateUserStats(UserDto dto) {
		return adminMapper.updateUserStatus(dto);
	}

	public int getPostCount(Map<String, Object> map) {
		return adminMapper.getPostCount(map);
	}

	public List<PostDto> getAllPosts(Map<String, Object> map) {
		return adminMapper.getAllPosts(map);
	}

	public int getCommentCount(Map<String, Object> map) {
		return adminMapper.getCommentCount(map);
	}

	public List<CommentDto> getAllComments(Map<String, Object> map) {
		return adminMapper.getAllComments(map);
	}

	public int deleteComment(int commentId) {
		return adminMapper.deleteComment(commentId);
	}

	public int getReviewCount(Map<String, Object> map) {
		return adminMapper.getReviewCount(map);
	}

	public List<ReviewDto> getAllReviews(Map<String, Object> map) {
		return adminMapper.getAllReviews(map);
	}

	public int deleteReview(int reviewId) {
		return adminMapper.deleteReview(reviewId);
	}

	public int getInquiryCount(Map<String, Object> map) {
		return adminMapper.getInquiryCount(map);
	}

	public List<InquiryDto> getAllInquiries(Map<String, Object> map) {
		return adminMapper.getAllInquiries(map);
	}

	public int getHandledInquiryCount(Map<String, Object> map) {
		return adminMapper.getHandledInquiryCount(map);
	}

	public int getUnhandledInquiryCount(Map<String, Object> map) {
		return adminMapper.getUnhandledInquiryCount(map);
	}

	public InquiryDto getInquiryById(int inquiryId) {
		return adminMapper.getInquiryById(inquiryId);
	}

	public int replyToInquiry(InquiryDto inquiryDto) {
		int result = adminMapper.replyToInquiry(inquiryDto);
		
		// 알림
		if (result > 0 && inquiryDto.getUserId() != null) {
			NotificationDto notif = new NotificationDto();
			notif.setSenderId(inquiryDto.getAdminId());
			notif.setReceiverId(inquiryDto.getUserId());
			notif.setNotiType("inquiryReply");
			notif.setNotiContent("1:1 문의 답변이 도착했습니다.");
			notif.setNotiUrl("/mypage?tab=inquiry"); 

			notificationService.sendNotification(notif);
		}

		return result;
	}

	public int getTotalReviewCount() {
		return adminMapper.getTotalReviewCount();
	}

	public int getTotalActiveUserCount() {
		return adminMapper.getTotalActiveUserCount();
	}

	public int getTotalBannedUserCount() {
		return adminMapper.getTotalBannedUserCount();
	}

	public int getTotalWithdrawnUserCount() {
		return adminMapper.getTotalWithdrawnUserCount();
	}
	
	public List<Map<String, Object>> getUserRegistrationCountPerDay() {
		return adminMapper.getUserRegistrationCountPerDay();
	}
	public List<Map<String, Object>> getPostCountByDate() {
		return adminMapper.getPostCountByDate();
	}
	public List<Map<String, Object>> getReviewCountByDate() {
		return adminMapper.getReviewCountByDate();
	}
}
