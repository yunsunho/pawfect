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
	public String content( /////// Num refer to post ID!!!!!!!
		@RequestParam int num, @RequestParam int pageNum,
		@RequestParam int number, HttpServletRequest request, Model model
		) throws Exception {
		PostDto postDto = boardService.getPost(num); // get post DTO with this ID
		
		model.addAttribute("num", num); ///////////// refers to postID
		// TODO: switch num to postID
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("number", number); 
		model.addAttribute("postDto", postDto);
		
		return "board/content";
	}
	
}
