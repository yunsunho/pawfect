package com.example.Pawfect.controller.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Pawfect.dto.PostDto;
import com.example.Pawfect.service.BoardService;

import jakarta.annotation.Resource;

@Controller
@RequestMapping("list")
public class BoardListController {
	@Resource
	private BoardService boardService;
	
	@GetMapping
	public String boardList(@RequestParam(required=false) String pageNum, Model model) {
		int count = 0;			// 전체 글 개수
		int currentPage = 0; 	// 계산용 페이지 번호
		int start = 0; 			// 출력할 페이지 첫 DB index
		int end = 0; 			// 출력할 페이지 마지막 DB index
		int number = 0; 		// 출력용 글 번호
		int pageCount = 0;		// 페이지 개수 
		int startPage = 0;		// 출력할 페이지 시작 번호
		int endPage = 0;		// 출력할 페이지 끝 번호 
		
		int pageSize = 10;
		int pageBlock = 3;
		
		count = boardService.getPostCount();
		
		if (pageNum == null || pageNum.equals("")) {
			pageNum = "1";
		}
		
		currentPage = Integer.parseInt(pageNum);
		start = (currentPage - 1) * pageSize + 1;
		end = start + pageSize - 1;
		if (end > count) {
			end = count;
		}
		
		number = count - (currentPage-1) * pageSize;
		pageCount = (count / pageSize) + (count%pageSize > 0 ? 1 : 0);				// 51/10
		startPage = (currentPage/pageBlock)*pageBlock + 1;
		
		if(currentPage % pageBlock == 0) {
			startPage -= pageBlock;
		}
		endPage = startPage + pageBlock - 1;
		
		if (endPage > pageCount) {
			endPage = pageCount;
		}
		
		model.addAttribute("count", count);
		model.addAttribute("number", number);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pageBlock", pageBlock);
		
		
		if (count > 0) {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("start", start);
			map.put("end", end);
			List<PostDto> dtos = boardService.getPosts(map);
			model.addAttribute("dtos", dtos);
		}
		
		return "board/list";
		
	}
}
