package com.example.Pawfect.controller.board;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Pawfect.dto.PostDto;
import com.example.Pawfect.service.BoardService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/board")
public class BoardContentController {
	@Resource
	private BoardService boardService;
	
	@GetMapping("/content")
	public String content( 
		@RequestParam int num, Model model
		) throws Exception {
		PostDto postDto = boardService.getPost(num); // get post DTO with this ID
		System.out.println("postDto postDisplayName: " + postDto.getDisplayName());
		System.out.println("postDTO likeCount: " + postDto.getLikeCount());
		System.out.println("postDTO commentCount: " + postDto.getCommentCount());
		System.out.println("postDTO postRegdate: " +postDto.getPostRegdate());
		
		model.addAttribute("num", num);
		model.addAttribute("postDto", postDto);
		
		return "board/content";
	}
	
}
