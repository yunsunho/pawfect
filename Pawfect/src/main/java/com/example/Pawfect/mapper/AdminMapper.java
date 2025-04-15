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
	public List<UserDto> getAllUsers(Map<String, Object> map);
	public List<PostDto> getAllPosts(Map<String, Object> map);
	public List<CommentDto> getAllComments(Map<String, Object> map);
	public List<ReviewDto> getAllReviews(Map<String, Object> map);
	public List<InquiryDto> getAllInquiries(Map<String, Object> map);
	public List<InquiryDto> getHandledInquiries(Map<String, Object> map);
	public List<InquiryDto> getUnhandledInquiries(Map<String, Object> map);
}
