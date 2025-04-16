package com.example.Pawfect.controller.admin;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Pawfect.dto.InquiryDto;
import com.example.Pawfect.dto.UserDto;
import com.example.Pawfect.service.AdminService;
import com.example.Pawfect.service.BoardService;

import jakarta.annotation.Resource;

@Controller
@RequestMapping("admin")
public class AdminUserController {
	@Resource
	private AdminService adminService;
	@Resource
	private BoardService boardService;
	
	@GetMapping("user")
	public String inquiry(
			@RequestParam(required = false) String pageNum,
			@RequestParam (required=false) String keyword,
			@RequestParam(required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, 
			@RequestParam(required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam(required=false) String sortBy,
			Model model) {
		int count = 0;			// 전체 글 개수 
		int currentPage = 0;	// 계산용 페이지 번호
		int start = 0;			// 출력할 페이지 첫 DB index
		int end = 0;			// 출력할 페이지 마지막 DB index
		int number = 0;			// 출력용 글 번호
		int pageCount = 0;		// 페이지 개수
		int startPage = 0;		// 출력할 페이지 시작 번호
		int endPage = 0;		// 출려할 페이지 끝 번호
		
		// Pagination settings
	    int pageSize = 10;         // Posts per page
	    int pageBlock = 3;         // Number of page links shown at once
	    
		
		Map<String, Object> map = new HashMap<>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("keyword", keyword);
		if (sortBy != null) {
			if (sortBy.equals("active")) {
				map.put("active", true);
			} else if (sortBy.equals("banned")) {
				map.put("banned", true);
			} else if (sortBy.equals("withdrawn")) {
				map.put("withdrawn", true);
			} 
		}
		count = adminService.getUserCount(map);
		
		if (pageNum == null || pageNum.equals("")) {
	    	pageNum = "1";
	    }
	    
	    currentPage = Integer.parseInt(pageNum);
	    
	    start = (currentPage - 1) * pageSize; // removed +1 (MySQL starts at 0)
	    end = start + pageSize - 1;
	    if (end > count) { // 계산보다 실제 글이 적은 경우
	    	end = count;
	    }
	    
	    number = count - (currentPage - 1) * pageSize;
	    
	    pageCount = (count / pageSize) + (count%pageSize > 0 ? 1:0);
	    startPage = (currentPage/pageBlock)*pageBlock + 1;
	    
	    if (currentPage % pageBlock == 0) {
	    	startPage -= pageBlock;
	    }
	    endPage = startPage + pageBlock - 1;
	    
	    if (endPage > pageCount) {
	    	endPage = pageCount;
	    }
	    
	    // Add pagination info to map
	    map.put("start", start);
	    map.put("pageSize", pageSize);
	    
	    if (count > 0) {
	    	List<UserDto> userDtos = adminService.getAllUsers(map);
	    	for (UserDto dto : userDtos) {
				dto.setFormattedDate(boardService.formatPostDate(dto.getUserRegdate()));
			}
	    	model.addAttribute("dtos", userDtos);
	    }
	    
	    
	    
	    // pagination info
	    model.addAttribute("count", count);
	    model.addAttribute("number", number);
	    model.addAttribute("pageNum", pageNum);
	    model.addAttribute("currentPage", currentPage);
	    model.addAttribute("pageCount", pageCount);
	    model.addAttribute("startPage", startPage);
	    model.addAttribute("endPage", endPage);
	    model.addAttribute("pageBlock", pageBlock);
	    
	    model.addAttribute("startDate", startDate);
	    model.addAttribute("endDate", endDate);
	    model.addAttribute("keyword", keyword);
	    
		return "admin/user";
	}
	
	@PostMapping("banUser")
	public String banUserform(@ModelAttribute UserDto userDto,
			@RequestParam int pageNum, Model model) {
		userDto.setUserStatus("BANNED");
		adminService.updateUserStats(userDto);
		
		return "redirect:/admin/user?pageNum=" + pageNum;

	}
}

