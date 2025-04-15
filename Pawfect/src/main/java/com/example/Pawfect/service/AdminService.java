package com.example.Pawfect.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Pawfect.dto.CommentDto;
import com.example.Pawfect.dto.InquiryDto;
import com.example.Pawfect.dto.PostDto;
import com.example.Pawfect.dto.ReviewDto;
import com.example.Pawfect.dto.UserDto;
import com.example.Pawfect.mapper.AdminMapper;

@Service
public class AdminService {
	@Autowired
	private AdminMapper adminMapper;
	
	public List<UserDto> getAllUsers(Map<String, Object> map) {
		return adminMapper.getAllUsers(map);
	}
	
	public List<PostDto> getAllPosts(Map<String, Object> map) {
		return adminMapper.getAllPosts(map);
	}
	
	public List<CommentDto> getAllComments(Map<String, Object> map) {
		return adminMapper.getAllComments(map);
	}
	
	public List<ReviewDto> getallReviews(Map<String, Object> map) {
		return adminMapper.getAllReviews(map);
	}
	
	public List<InquiryDto> getAllInquiries(Map<String, Object> map) {
		return adminMapper.getAllInquiries(map);
	}
	
	public List<InquiryDto> getHandledInquiries(Map<String, Object> map) {
		return adminMapper.getHandledInquiries(map);
	}
	
	public List<InquiryDto> getUnhandledInquiries(Map<String, Object> map) {
		return adminMapper.getUnhandledInquiries(map);
	}
}
