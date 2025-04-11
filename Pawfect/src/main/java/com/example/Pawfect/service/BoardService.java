package com.example.Pawfect.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Pawfect.dto.PostDto;
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
	
	/*
	public List<PostDto> getPosts(Map<String, Integer> map) {
		return boardMapper.getAllPosts(map);
		
	}
	*/

	public void savePost(PostDto postDto) {
		// TODO Auto-generated method stub
	}

	
	
	// For stats at the top of the page
	public int getTotalPostCount() {
		return boardMapper.getTotalPostCount();
	}
	
	public int getTotalCommentCount() {
		return boardMapper.getTotalCommentCount();
	}

	public int getTotalUserCount() {
		return boardMapper.getTotalUserCount();
	}
	
	// get like count of single post
	public int getTotalLikeCount(int postId) {
		return boardMapper.getTotalLikeCount(postId);
	}
}
