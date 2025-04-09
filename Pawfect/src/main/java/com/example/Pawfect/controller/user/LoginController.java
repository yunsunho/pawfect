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

	// 로그인 결과 처리 (실패 메시지)
	@GetMapping("/loginResult")
	public String loginResult(@RequestParam("status") String status, @RequestParam(required = false) String error,
			Model model) {

		String message;

		if ("failure".equals(status)) {
			message = switch (error) {
			case "userNotFound" -> "존재하지 않는 아이디입니다. 다시 확인해주세요.";
			case "invalidCredentials" -> "일치하지 않는 비밀번호입니다. 다시 확인해주세요.";
			case "accountBanned" -> "정지된 계정입니다.";
			case "accountWithdrawn" -> "탈퇴한 계정입니다.";
			default -> "로그인 실패! 다시 시도해주세요.";
			};
		} else {
			message = null;
		}

		model.addAttribute("status", status);
		model.addAttribute("message", message);
		return "user/loginResult";
	}
}
