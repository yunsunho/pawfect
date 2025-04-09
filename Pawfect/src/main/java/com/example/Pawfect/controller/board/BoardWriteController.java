package com.example.Pawfect.controller.board;

import java.sql.Timestamp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Pawfect.dto.PostDto;
import com.example.Pawfect.service.BoardService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
public class BoardWriteController {
	@Resource
	private BoardService boardService;
	
	@GetMapping("/writeForm")
    public String writeForm() {
        return "board/writeForm";
    }
	
	@GetMapping("/writePro")
	public String writePro() {
		return "board/writePro";
	}
	
	@PostMapping("/save")
	public String savePost(PostDto postDto, HttpSession session, Model model) {
	    try {
	        String userId = (String) session.getAttribute("userId");
	        postDto.setUserId(userId);
	        postDto.setPostRegdate(new Timestamp(System.currentTimeMillis()));
	        postDto.setPostViewCount(0);
	        boardService.savePost(postDto);
	        model.addAttribute("result", 1); // success
	    } catch (Exception e) {
	        model.addAttribute("result", 0); // fail
	    }
	    return "post/writePro"; // JSP page to show alert and redirect
	}
	
	/*
	@GetMapping
	public String writeForm() {
		// TODO
	}*/
}
