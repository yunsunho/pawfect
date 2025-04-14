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
	
	// stats
	public int getTotalPostCount();
	public int getTotalCommentCount();
	public int getTotalUserCount();
	
	public int getTotalLikeCount(int postId);
	
	public CommentDto getCommentById(int commentId);
	public List<CommentDto> getComments(int postId);
	public int getUserCommentCount(Map<String, Object> map);
	public List<CommentDto> getUserComments(Map<String, Object> map);
	
	public int insertComment(CommentDto commentDto);
	public int updateCommentRef(int commentId);
	public int updateReStep(Map<String, Object> map);
	public int insertReplyComment(CommentDto commentDto);
	
	public int countUserLike(Map<String, Object> map);
	public int addLike(Map<String, Object> map);
	public int removeLike(Map<String, Object> map);
	
	public List<PostDto> getHotPosts();

}
