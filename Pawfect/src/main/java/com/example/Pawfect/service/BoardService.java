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
	
	public int getPostCount() {
		return boardMapper.getPostCount();
	}
	
	public int insertPost(PostDto postDto) {
		return boardMapper.insertPost(postDto);
	}
	
	public PostDto getPost(int num) {
		return boardMapper.getPost(num);
	}
	
	public List<PostDto> getPosts(Map<String, Integer> map) {
		return boardMapper.getAllPosts(map);
		
	}

	public void savePost(PostDto postDto) {
		// TODO Auto-generated method stub
		
	}

}
