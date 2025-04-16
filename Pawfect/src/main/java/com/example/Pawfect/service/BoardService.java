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
import com.example.Pawfect.dto.NotificationDto;
import com.example.Pawfect.dto.PostDto;
import com.example.Pawfect.dto.UserDto;
import com.example.Pawfect.mapper.BoardMapper;

@Service
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;

	@Autowired
	private NotificationService notificationService;

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

		// 알림
		PostDto post = boardMapper.getPost(comment.getPostId());
		if (!post.getUserId().equals(comment.getUserId())) {
			NotificationDto notif = new NotificationDto();
			notif.setSenderId(comment.getUserId());
			notif.setReceiverId(post.getUserId());
			notif.setNotiType("comment");
			notif.setNotiContent(
					comment.getUserNickname() + "(" + maskId(comment.getUserId()) + ")님이 내 게시글에 댓글을 달았습니다.");
			notif.setNotiUrl("/board/content?num=" + comment.getPostId());

			notificationService.sendNotification(notif);
		}

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

		// 알림
		comment.setCom_ref(com_ref);
		comment.setCom_re_step(com_re_step + 1);
		comment.setCom_re_level(com_re_level + 1);

		if (!parent.getUserId().equals(comment.getUserId()) && !parent.isComDeleteStatus()) {
			PostDto post = boardMapper.getPost(comment.getPostId());

			NotificationDto notif = new NotificationDto();
			notif.setSenderId(comment.getUserId());
			notif.setReceiverId(parent.getUserId());
			notif.setNotiType("reply");
			notif.setNotiContent(
					comment.getUserNickname() + "(" + maskId(comment.getUserId()) + ")님이 내 댓글에 답글을 달았습니다.");
			notif.setNotiUrl("/board/content?num=" + comment.getPostId());

			notificationService.sendNotification(notif);
		}
		return boardMapper.insertReplyComment(comment);
	}

	// 마스킹 함수
	private String maskId(String userId) {
		if (userId == null || userId.length() < 3)
			return userId + "***";
		return userId.substring(0, 3) + "***";
	}

	public int deleteComment(int commendId) {
		return boardMapper.deleteComment(commendId);
	}

	public int modifyComment(CommentDto commentDto) {
		return boardMapper.modifyComment(commentDto);
	}

	public int userLikedPost(String userId, int postId) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("postId", postId);
		return boardMapper.countUserLike(map);
	}

	public int getUserCommentCount(Map<String, Object> map) {
		return boardMapper.getUserCommentCount(map);
	}

	public List<CommentDto> getUserComments(Map<String, Object> map) {
		return boardMapper.getUserComments(map);
	}

	public int addLike(String userId, int postId) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("postId", postId);
		return boardMapper.addLike(map);
	}

	public int removeLike(String userId, int postId) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("postId", postId);
		return boardMapper.removeLike(map);
	}

	public List<PostDto> getHotPosts() {
		return boardMapper.getHotPosts();
	}

	public String generateDisplayName(String userId, String userNickname) {
		String maskedId = (userId != null && userId.length() >= 3) ? userId.substring(0, 3) + "***" : userId + "***";
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

	// 메인 인기글
	public List<PostDto> getTopPostsByViews() {
		List<PostDto> list = boardMapper.getTopPostsByViews(2);
		for (PostDto post : list) {
			post.generateDisplayName();
		}
		return list;
	}

	public List<PostDto> getTopPostsByLikes() {
		List<PostDto> list = boardMapper.getTopPostsByLikes(2);
	    for (PostDto post : list) {
	        post.generateDisplayName();
	    }
	    return list;
	}

	public List<PostDto> getTopPostsByComments() {
		List<PostDto> list = boardMapper.getTopPostsByComments(2);
	    for (PostDto post : list) {
	        post.generateDisplayName();
	    }
	    return list;
	}
}
