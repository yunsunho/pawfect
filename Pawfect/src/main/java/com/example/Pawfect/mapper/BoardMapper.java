package com.example.Pawfect.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.Pawfect.dto.CommentDto;
import com.example.Pawfect.dto.PostDto;

@Mapper
public interface BoardMapper {
	public PostDto getPost(int num);
	public List<PostDto> getPosts(Map<String, Object> filterMap);
	public int getPostCount(Map<String, Object> filterMap);
	
	public int insertPost(PostDto postDto);
	public int modifyPost(PostDto postDto);
	public int deletePost(int num);
	
	public int incrementViewCount(int postId);
	
	//public int maxNum();
	//public void addReply(PostDto postDto);
	
	
	//public void addCount(int num);
	//public int checkReply(PostDto postDto);
	//public void deleteReply(PostDto postDto);
	
	
	
	
	// for total post
	public int getTotalPostCount();
	public int getTotalCommentCount();
	public int getTotalUserCount();
	
	public int getTotalLikeCount(int postId);
	
	public CommentDto getCommentById(int commentId);
	public List<CommentDto> getComments(int postId);
	public int insertComment(CommentDto commentDto);
	public int updateCommentRef(int commentId);
	public int updateReStep(Map<String, Object> map);
	public int insertReplyComment(CommentDto commentDto);
	

}
