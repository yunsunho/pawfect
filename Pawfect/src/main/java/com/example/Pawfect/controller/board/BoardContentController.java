package com.example.Pawfect.controller.board;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Pawfect.dto.CommentDto;
import com.example.Pawfect.dto.PostDto;
import com.example.Pawfect.dto.UserDto;
import com.example.Pawfect.service.BoardService;

import jakarta.annotation.Resource;

@Controller
@RequestMapping("/board")
public class BoardContentController {
	@Resource
	private BoardService boardService;
	
	@GetMapping("/content")
	public String content( 
		@RequestParam int num, Model model
		) throws Exception {
		PostDto postDto = boardService.getPost(num);
		postDto.setFormattedDate(boardService.formatPostDate(postDto.getPostRegdate()));
		
		UserDto userDto = boardService.getLoggedInUser();
		boolean isLoggedIn = (userDto!=null);
		boolean isUserPost = userDto!=null && postDto.getUserId().equals(userDto.getUserId());
		
		if (!isUserPost) {
			boardService.incrementViewCount(num);
		}
		
		int userLiked = 0;
		if (isLoggedIn) {
			userLiked = boardService.userLikedPost(userDto.getUserId(), postDto.getPostId());
		}
		
		
		// Stats
	    int totalPosts = boardService.getTotalPostCount();
	    int totalComments = boardService.getTotalCommentCount();
	    int totalUsers = boardService.getTotalUserCount();
	    List<PostDto> hottestPosts = boardService.getHotPosts();
	    model.addAttribute("totalPosts", totalPosts);
	    model.addAttribute("totalComments", totalComments);
	    model.addAttribute("totalUsers", totalUsers);
	    model.addAttribute("hottestPosts", hottestPosts);
		
	    // Modify / Delete post
		model.addAttribute("num", num);
		model.addAttribute("postDto", postDto);
		model.addAttribute("userDto", userDto);
		model.addAttribute("isUserPost", isUserPost);
		model.addAttribute("userLiked", userLiked);
		model.addAttribute("isLoggedIn", isLoggedIn);
		
		
		// Comment section
		List<CommentDto> commentDtos = boardService.getComments(num);
		for (CommentDto dto : commentDtos) {
			dto.setDisplayName(boardService.generateDisplayName(dto.getUserId(), dto.getUserNickname()));
			dto.setFormattedDate(boardService.formatPostDate(dto.getComRegdate()));
		}
		
		model.addAttribute("commentDtos", commentDtos);
		
		return "board/content";
	}
	
	@PostMapping("/comment")
	public String comment(@ModelAttribute CommentDto commentDto, Model model) {
		UserDto userDto = boardService.getLoggedInUser();
		commentDto.setUserId(userDto.getUserId());
		commentDto.setUserNickname(userDto.getUserNickname());
		
		int result = boardService.insertTopLevelComment(commentDto);
		String msg = null;
		String redirectUrl = "/board/content?num=" + commentDto.getPostId();
		
		if (result != 1) {
			msg = "댓글 등록에 실패하였습니다. 다시 시도해주세요.";
		} 
		
		model.addAttribute("msg", msg);
		model.addAttribute("redirectUrl", redirectUrl);
		
		return "board/message";
	}
	
	@PostMapping("/reply")
	public String reply(@ModelAttribute CommentDto commentDto, Model model) {
		
		UserDto userDto = boardService.getLoggedInUser();
		if (userDto == null) {
			return "redirect:/loginForm";
		}
		commentDto.setUserId(userDto.getUserId());
		commentDto.setUserNickname(userDto.getUserNickname());	
		int parentCommentId = commentDto.getParentCommentId();
		
		int result = boardService.insertReplyComment(commentDto, parentCommentId);
		String msg = null;
		String redirectUrl = "/board/content?num=" + commentDto.getPostId();
		
		if (result != 1) {
			msg = "답글 등록에 실패하였습니다. 다시 시도해주세요.";
		} 
		
		model.addAttribute("msg", msg);
		model.addAttribute("redirectUrl", redirectUrl);
		
		return "board/message";
	}
	
	@PostMapping("/deletecomment")
	public String deleteComment(@RequestParam int commentId, 
		@RequestParam int num, Model model) {
		int result = boardService.deleteComment(commentId);
		
		String msg = null;
		String redirectUrl = "/board/content?num=" + num;
		if (result != 1) {
			msg = "댓글 삭제에 실패하였습니다. 다시 시도해주세요.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("redirectUrl", redirectUrl);
		return "board/message";
	}
	
	@PostMapping("/modifycomment")
	public String modifyComment(@ModelAttribute CommentDto commentDto,
			@RequestParam int num, Model model) {
		int result = boardService.modifyComment(commentDto);
		String msg = null;
		String redirectUrl = "/board/content?num=" + num;
		if (result != 1) {
			msg = "댓글 삭제에 실패하였습니다. 다시 시도해주세요.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("redirectUrl", redirectUrl);
		return "board/message";
	}	
	
	@PostMapping("/like")
	public String like(@RequestParam int num, Model model) {
		UserDto userDto = boardService.getLoggedInUser();
		PostDto postDto = boardService.getPost(num);
		int userLiked = boardService.userLikedPost(userDto.getUserId(), postDto.getPostId());
		
		int result;
		
		if (userLiked == 1) {
			result = boardService.removeLike(userDto.getUserId(), postDto.getPostId());
		} else {
			result = boardService.addLike(userDto.getUserId(), postDto.getPostId());
		}
		
		String msg = null;
		String redirectUrl = "/board/content?num=" + num;
		if (result != 1) {
			msg = "답글 등록에 실패하였습니다. 다시 시도해주세요.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("redirectUrl", redirectUrl);
		
		return "board/message";
	}
	
}
