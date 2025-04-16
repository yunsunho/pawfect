package com.example.Pawfect.controller.board;

import java.util.List;

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
	    List<PostDto> hottestPosts = boardService.getHotPosts();
	    model.addAttribute("totalPosts", totalPosts);
	    model.addAttribute("totalComments", totalComments);
	    model.addAttribute("totalUsers", totalUsers);
	    model.addAttribute("hottestPosts", hottestPosts);
		
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
		String msg=null;
		
		if (result == 0) {
			msg = "게시물 수정에 실패하였습니다. 다시 시도해주세요.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("redirectUrl", "/board");
		
		return "board/message";
	}
}
