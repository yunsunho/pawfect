package com.example.Pawfect.controller.board;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Pawfect.dto.PostDto;
import com.example.Pawfect.dto.UserDto;
import com.example.Pawfect.service.BoardService;

import jakarta.annotation.Resource;

@Controller
@RequestMapping("board")
public class BoardModifyController {
	@Resource
	private BoardService boardService;
	
	@GetMapping("modify")
	public String modifyForm(@RequestParam Integer num, Model model) {
		PostDto postDto = boardService.getPost(num);
		UserDto userDto = boardService.getLoggedInUser();
		
		// Stats
	    int totalPosts = boardService.getTotalPostCount();
	    int totalComments = boardService.getTotalCommentCount();
	    int totalUsers = boardService.getTotalUserCount();
	    model.addAttribute("totalPosts", totalPosts);
	    model.addAttribute("totalComments", totalComments);
	    model.addAttribute("totalUsers", totalUsers);
		
		// Return to main board if not user's post
		if (userDto == null || !postDto.getUserId().equals(userDto.getUserId())) {
			return "board/list";
		}
		
		model.addAttribute("postDto", postDto);
		model.addAttribute("userDto", userDto);
		
		
		return "board/modifyForm";
	}
	
	@PostMapping("modify")
	public String modifyPro(@ModelAttribute PostDto postDto, Model model) {
		int result = boardService.modifyPost(postDto);
		model.addAttribute("result", result);
		
		return "board/modifyPro";
	}
}
