package com.example.Pawfect.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.Pawfect.dto.PostDto;

@Mapper
public interface BoardMapper {
	public PostDto getPost(int num);
	public List<PostDto> getPosts(Map<String, Object> filterMap);
	public int getPostCount(Map<String, Object> filterMap);
	
	
	
	//public int getPostCount();
	public int maxNum();
	public void addReply(PostDto postDto);
	public int insertPost(PostDto postDto);
	//public List<PostDto> getAllPosts(Map<String, Integer> map);
	public void addCount(int num);
	public int modifyPost(PostDto postDto);
	public int checkReply(PostDto postDto);
	public void deleteReply(PostDto postDto);
	public int deletePost(int num);
	
	
	
	
	// for total post
	public int getTotalPostCount();
	public int getTotalCommentCount();
	public int getTotalUserCount();
	
	// like count per post
	public int getTotalLikeCount(int postId);

}
