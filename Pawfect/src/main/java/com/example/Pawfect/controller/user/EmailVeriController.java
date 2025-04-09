package com.example.Pawfect.controller.user;

import com.example.Pawfect.service.EmailVeriService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/email-verification")
public class EmailVeriController {

	private final EmailVeriService emailVeriService;

	// 이메일 인증 메일 발송
	@PostMapping("/send")
	public ResponseEntity<String> sendVerificationEmail(@RequestParam String email) {
		try {
			emailVeriService.sendVerificationEmail(email); // 이메일 인증 코드 전송
			return ResponseEntity.ok("이메일 인증 메일을 발송했습니다.");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("이메일 전송 실패: " + e.getMessage());
		}
	}

	// 인증 코드 확인 및 처리
	@PostMapping("/verify")
	public ResponseEntity<String> verifyEmailCode(@RequestParam String email, @RequestParam String code) {
		boolean isVerified = emailVeriService.verifyEmailCode(email, code); // 인증 코드 검증
		if (isVerified) {
			emailVeriService.markAsVerified(email); // 인증 완료 처리
			return ResponseEntity.ok("이메일 인증이 완료되었습니다.");
		} else {
			return ResponseEntity.status(400).body("잘못된 인증 코드입니다.");
		}
	}

	// 이메일 인증 여부 확인
	@GetMapping("/status")
	public ResponseEntity<String> checkVerificationStatus(@RequestParam String email) {
		boolean isVerified = emailVeriService.isEmailVerified(email); // 이메일 인증 상태 확인
		if (isVerified) {
			return ResponseEntity.ok("이메일 인증 완료");
		} else {
			return ResponseEntity.status(400).body("이메일 인증이 완료되지 않았습니다.");
		}
	}
}
