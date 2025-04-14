package com.example.Pawfect.service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.Pawfect.auth.CustomUserDetails;
import com.example.Pawfect.dto.CommentDto;
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
	
	public List<CommentDto> getComments(int postId) {
		return boardMapper.getComments(postId);
	}

	public int insertTopLevelComment(CommentDto comment) {
		comment.setCom_re_step(0);
		comment.setCom_re_level(0);
		int result = boardMapper.insertComment(comment);
		
		boardMapper.updateCommentRef(comment.getCommentId());
		
		return result;
	}
	
	public int insertReplyComment(CommentDto comment, int parentCommentId) {
		CommentDto parent = boardMapper.getCommentById(parentCommentId);
		
		if (parent == null) {
			throw new IllegalArgumentException("Parent comment not found");
		}
		
		int com_ref = parent.getCom_ref(); // group ID
		int com_re_step = parent.getCom_re_step();
		int com_re_level = parent.getCom_re_level();
		
		// shift steps to make space
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("com_ref", com_ref);
		paramMap.put("com_re_step", com_re_step);
		
	    boardMapper.updateReStep(paramMap);
		
		// insert new reply
		comment.setCom_ref(com_ref);
		comment.setCom_re_step(com_re_step + 1);
		comment.setCom_re_level(com_re_level + 1);
		
		return boardMapper.insertReplyComment(comment);
	}
	
	public String generateDisplayName(String userId, String userNickname) {
		String maskedId = (userId != null && userId.length() >=3)
			? userId.substring(0, 3) + "***"
			: userId + "***";
		return userNickname + " (" + maskedId + ")";
	}
	
	public String formatPostDate(Timestamp timestamp) {
	    LocalDateTime postDateTime = timestamp.toLocalDateTime();
	    LocalDateTime now = LocalDateTime.now();

	    if (postDateTime.toLocalDate().equals(now.toLocalDate())) {
	        long hoursAgo = Duration.between(postDateTime, now).toHours();
	        if (hoursAgo == 0) {
	            long minutesAgo = Duration.between(postDateTime, now).toMinutes();
	            return minutesAgo + "분 전";
	        }
	        return hoursAgo + "시간 전";
	    } else {
	        return postDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	    }
	}
}
