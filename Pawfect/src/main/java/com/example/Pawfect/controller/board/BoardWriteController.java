package com.example.Pawfect.controller.board;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Pawfect.dto.PostDto;
import com.example.Pawfect.dto.UserDto;
import com.example.Pawfect.service.BoardService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
public class BoardWriteController {
	@Resource
	private BoardService boardService;
	
	@GetMapping("/write")
    public String writeForm(Model model) {
		// Stats
		int totalPosts = boardService.getTotalPostCount();
	    int totalComments = boardService.getTotalCommentCount();
	    int totalUsers = boardService.getTotalUserCount();
	    model.addAttribute("totalPosts", totalPosts);
	    model.addAttribute("totalComments", totalComments);
	    model.addAttribute("totalUsers", totalUsers);
	    
        return "board/writeForm";
    }
	
	@PostMapping("/write")
	public String writePro(@ModelAttribute PostDto postDto, Model model) {
		
		UserDto userDto = boardService.getLoggedInUser();
		String userId = userDto.getUserId();
		
		postDto.setUserId(userId);
		postDto.generateDisplayName();
		
		int result = boardService.insertPost(postDto);
		model.addAttribute("result", result);
		
		return "board/writePro";
	}
}


