package com.example.Pawfect.controller.board;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Pawfect.service.BoardService;

import jakarta.annotation.Resource;

@Controller
@RequestMapping("board")
public class BoardDeleteController {
	@Resource
	private BoardService boardService;
	
	@GetMapping("delete")
	public String deletePost(@RequestParam Integer num, Model model) {
		int result = boardService.deletePost(num);
		String msg = null;
		
		if (result == 0) {
			msg = "게시글 삭제에 실패하였습니다. 다시 시도해주세요.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("redirectUrl", "/board");
		
		return "board/message";
	}
}
