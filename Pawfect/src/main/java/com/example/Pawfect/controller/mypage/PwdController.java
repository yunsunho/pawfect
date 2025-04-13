package com.example.Pawfect.controller.mypage;

import com.example.Pawfect.auth.CustomUserDetails;
import com.example.Pawfect.dto.PwdUpdateDto;
import com.example.Pawfect.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class PwdController {

	private final MyPageService myPageService;

	@GetMapping("/tab/password")
	public String showPwdTab() {
		return "mypage/mypage_pwd";
	}

	@PostMapping("/password/update")
	@ResponseBody
	public String updatePassword(@AuthenticationPrincipal CustomUserDetails userDetails,
			@RequestBody PwdUpdateDto dto) {

		String userId = userDetails.getUser().getUserId();
		dto.setUserId(userId);

		// 현재 비밀번호 확인
		boolean currentPwdMatches = myPageService.checkPwdMatch(userId, dto.getCurrentPwd());
		if (!currentPwdMatches) {
			return "wrong"; 
		}

		// 새 비번 == 새 비번 확인
		if (!dto.getNewPwd().equals(dto.getConfirmPwd())) {
			return "mismatch";
		}

		// 비번 변경 처리
		boolean updated = myPageService.updatePwd(userId, dto.getNewPwd());
		return updated ? "success" : "fail";
	}
}
