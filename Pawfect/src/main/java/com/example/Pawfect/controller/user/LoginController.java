package com.example.Pawfect.controller.user;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@GetMapping("/loginForm")
	public String loginForm(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "message", required = false) String message, Model model) {
		model.addAttribute("error", error);
		model.addAttribute("message", message);
		return "user/loginForm";
	}
	
	@PostMapping("/login/setRedirect")
	@ResponseBody
	public void setRedirect(@RequestBody Map<String, String> data, HttpSession session) {
	    session.setAttribute("afterLoginRedirect", data.get("url"));
	}

}
