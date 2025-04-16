package com.example.Pawfect.controller.admin;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Pawfect.dto.InquiryDto;
import com.example.Pawfect.service.AdminService;
import com.example.Pawfect.service.BoardService;

import jakarta.annotation.Resource;

@Controller
@RequestMapping("admin")
public class AdminDashboardController {
	@Resource
	private AdminService adminService;
	
	@Resource
	private BoardService boardService;
	
	@GetMapping
	public String dashboard(Model model) {
		String adminName = boardService.getLoggedInUser().getUserName();
		
		// STATS
		int activeUserCount = adminService.getTotalActiveUserCount(); // 활동
		int bannedUserCount = adminService.getTotalBannedUserCount(); // 정지
		int withdrawnUserCount = adminService.getTotalWithdrawnUserCount(); // 탈퇴 
		int totalReviewCount = adminService.getTotalReviewCount();
		int totalPostCount = boardService.getTotalPostCount();
		int totalCommentCount = boardService.getTotalCommentCount();
		
		model.addAttribute("adminName", adminName);
		model.addAttribute("activeUserCount", activeUserCount);
		model.addAttribute("bannedUserCount", bannedUserCount);
		model.addAttribute("withdrawnUserCount", withdrawnUserCount);
		model.addAttribute("totalReviewCount", totalReviewCount);
		model.addAttribute("totalPostCount", totalPostCount);
		model.addAttribute("totalCommentCount", totalCommentCount);
		
		// chart.js
		List<Map<String, Object>> userData = adminService.getUserRegistrationCountPerDay();
		Map<String, Integer> userRegistrationData = new LinkedHashMap<>();
		for (Map<String, Object> row : userData) {
		    String date = row.get("regDate").toString();
		    Integer count = ((Number) row.get("userCount")).intValue();
		    userRegistrationData.put(date, count);
		}
		model.addAttribute("userRegistrationData", userRegistrationData);
		
		return "admin/dashboard";
	}
}
