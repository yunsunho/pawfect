package com.example.Pawfect.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Pawfect.service.AdminService;

import jakarta.annotation.Resource;

@Controller
@RequestMapping("admin")
public class AdminDashboardController {
	@Resource
	private AdminService adminService;
	
	@GetMapping
	public String dashboard() {
		return "admin/dashboard";
	}
	
}
