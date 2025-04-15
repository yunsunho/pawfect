package com.example.Pawfect.controller.mypage;

import com.example.Pawfect.auth.CustomUserDetails;
import com.example.Pawfect.dto.InfoUpdateDto;
import com.example.Pawfect.dto.MyPageUserDto;
import com.example.Pawfect.service.MyPageService;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class InfoController {

	private final MyPageService myPageService;

	// 내 정보 탭 로딩
	@GetMapping("/tab/info")
	public String loadInfoTab(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
		if (userDetails == null) {
			return "redirect:/loginForm";
		}

		String userId = userDetails.getUser().getUserId();
		MyPageUserDto user = myPageService.getUserSummary(userId);
		model.addAttribute("user", user);

		return "mypage/mypage_info";
	}

	// 이메일 또는 전화번호 수정 요청 처리
	@PostMapping("/info/update")
	@ResponseBody
	public String updateUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails,
			@RequestBody InfoUpdateDto dto) {
		if (userDetails == null) {
			return "unauthenticated";
		}

		dto.setUserId(userDetails.getUser().getUserId());

		boolean result = myPageService.updateUserInfo(dto);
		return result ? "success" : "fail";
	}

	// 탈퇴 비밀번호 검증
	@PostMapping("/info/check-password")
	@ResponseBody
	public String checkPassword(@AuthenticationPrincipal CustomUserDetails userDetails,
			@RequestBody Map<String, String> body) {
		if (userDetails == null)
			return "unauthenticated";

		String inputPwd = body.get("pwd");
		boolean match = myPageService.checkPwdMatch(userDetails.getUser().getUserId(), inputPwd);
		return match ? "match" : "mismatch";
	}

	// 탈퇴 요청
	@PostMapping("/info/withdraw")
	@ResponseBody
	public String withdraw(@AuthenticationPrincipal CustomUserDetails userDetails) {
		if (userDetails == null)
			return "unauthenticated";

		String userId = userDetails.getUser().getUserId();
		boolean result = myPageService.withdrawUser(userId);
		return result ? "success" : "fail";
	}
}
