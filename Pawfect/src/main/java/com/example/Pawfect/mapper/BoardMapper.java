package com.example.Pawfect.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.Pawfect.dto.PostDto;

@Mapper
public interface BoardMapper {
	public int getPostCount();
	public int maxNum();
	public void addReply(PostDto postDto);
	public int insertPost(PostDto postDto);
	public List<PostDto> getAllPosts(Map<String, Integer> map);
	public PostDto getPost(int num);
	public void addCount(int num);
	public int modifyPost(PostDto postDto);
	public int checkReply(PostDto postDto);
	public void deleteReply(PostDto postDto);
	public int deletePost(int num);
}
