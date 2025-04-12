package com.example.Pawfect.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.Pawfect.auth.CustomUserDetails;
import com.example.Pawfect.dto.PostDto;
import com.example.Pawfect.dto.UserDto;
import com.example.Pawfect.mapper.BoardMapper;

@Service
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	
	public PostDto getPost(int num) {
		PostDto dto = boardMapper.getPost(num);
		dto.generateDisplayName();
		return dto;
	}
	public List<PostDto> getPosts(Map<String, Object> filterMap) {
		return boardMapper.getPosts(filterMap);
	}
	public int getPostCount(Map<String, Object> map) {
		return boardMapper.getPostCount(map);
	}
	
	public int insertPost(PostDto postDto) {
		return boardMapper.insertPost(postDto);
	}
	
	public int modifyPost(PostDto postDto) {
		return boardMapper.modifyPost(postDto);
	}
	
	public int deletePost(int postId) {
		return boardMapper.deletePost(postId);
	}
	
	public int incrementViewCount(int postId) {
		return boardMapper.incrementViewCount(postId);
	}
	
	public int getTotalLikeCount(int postId) {
		return boardMapper.getTotalLikeCount(postId);
	}
	
	// sidebar stats
	public int getTotalPostCount() {
		return boardMapper.getTotalPostCount();
	}
	
	public int getTotalCommentCount() {
		return boardMapper.getTotalCommentCount();
	}

	public int getTotalUserCount() {
		return boardMapper.getTotalUserCount();
	}
	
	
	// for write / modify / delete form
	public UserDto getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
			return userDetails.getUser();
		}
		
		return null;
	}
}
