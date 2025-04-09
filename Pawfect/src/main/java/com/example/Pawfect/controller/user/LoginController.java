package com.example.Pawfect.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

	@GetMapping("/loginForm")
	public String loginForm(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "message", required = false) String message, Model model) {
		model.addAttribute("error", error);
		model.addAttribute("message", message);
		return "user/loginForm";
	}
}
