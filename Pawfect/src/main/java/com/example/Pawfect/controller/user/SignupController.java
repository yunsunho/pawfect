package com.example.Pawfect.controller.user;

import com.example.Pawfect.dto.UserDto;
import com.example.Pawfect.service.EmailVeriService;
import com.example.Pawfect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequiredArgsConstructor
public class SignupController {

	private final UserService userService;
	private final EmailVeriService emailVeriService;

	// 회원가입 폼
	@GetMapping("/signup")
	public String signupForm() {
		return "user/signupForm"; 
	}

	// 회원가입 처리
	@PostMapping("/signup")
	public String signup(@ModelAttribute UserDto user, Model model) {
		// 필수 항목 검사
		if (user.getUserId() == null || user.getUserId().isBlank() || user.getPwd() == null || user.getPwd().isBlank()
				|| user.getUserName() == null || user.getUserName().isBlank() || user.getEmail() == null
				|| user.getEmail().isBlank()) {

			model.addAttribute("error", "모든 필수 정보를 입력해주세요.");
			return "user/signupForm"; 
		}
		
		// 아이디 중복 확인
		if (userService.isUserIdDuplicated(user.getUserId())) {
			model.addAttribute("error", "이미 사용 중인 아이디입니다.");
			return "user/signupForm"; 
		}
		
		// 이메일 중복 확인
		if (userService.isEmailDuplicated(user.getEmail())) {
			model.addAttribute("error", "이미 사용 중인 이메일입니다.");
			return "user/signupForm"; 
		}
		
		// 이메일 인증 여부 확인
		if (!emailVeriService.isEmailVerified(user.getEmail())) {
			model.addAttribute("error", "이메일 인증을 완료해야 합니다.");
			return "user/signupForm";
		}
		
		// 회원가입 처리
		user.setUserStatus("ACTIVE");
		user.setAdmin(false);
		userService.registerUser(user);

		// 회원가입 성공 후 리다이렉트
		try {
			String message = URLEncoder.encode("회원가입이 완료되었습니다!", "UTF-8");
			return "redirect:/signupResult?status=success&message=" + message;
		} catch (UnsupportedEncodingException e) {
			return "redirect:/signupResult?status=success&message=success";
		}

	}

	// 아이디 중복 확인
	@GetMapping("/checkId")
	@ResponseBody
	public String checkId(@RequestParam("userId") String userId) {
		boolean isDuplicate = userService.isUserIdDuplicated(userId);
		return isDuplicate ? "이미 사용 중인 아이디입니다." : "사용 가능한 아이디입니다.";
	}
	
	// 이메일 중복 확인
	@GetMapping("/checkEmail")
	@ResponseBody
	public String checkEmail(@RequestParam("email") String email) {
		boolean isDuplicate = userService.isEmailDuplicated(email);
		return isDuplicate ? "이미 사용 중인 이메일입니다." : "사용 가능한 이메일입니다.";
	}

	// 이메일 인증 전송
	@GetMapping("/sendAuthEmail")
	@ResponseBody
	public String sendAuthEmail(@RequestParam("email") String email) {
		emailVeriService.sendVerificationEmail(email); // 이메일 인증 요청
		return "📧 인증 이메일이 전송되었습니다. 메일함을 확인해주세요!";
	}

	// 인증 코드 확인 메서드
	@PostMapping("/verifyCode")
	@ResponseBody
	public String verifyEmailCode(@RequestParam("email") String email, @RequestParam("code") String inputCode) {
		boolean isVerified = emailVeriService.verifyEmailCode(email, inputCode); // 이메일 인증 코드 확인

		if (isVerified) {
			emailVeriService.markAsVerified(email);
			return "success"; 
		} else {
			return "인증 코드가 잘못되었습니다. 다시 시도해주세요.";
		}
	}

	// 회원가입 결과 처리 페이지
	@GetMapping("/signupResult")
	public String signupResult(@RequestParam("status") String status, @RequestParam("message") String message,
			Model model) {
		model.addAttribute("status", status);
		model.addAttribute("message", message);
		return "user/signupResult"; // 결과 페이지로 리턴
	}
}
