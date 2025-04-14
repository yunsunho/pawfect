package com.example.Pawfect.controller.mypage;

import com.example.Pawfect.auth.CustomUserDetails;
import com.example.Pawfect.dto.InquiryDto;
import com.example.Pawfect.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class InquiryController {
	private final MyPageService myPageService;

	// 내가 작성한 문의 목록 보기
	@GetMapping("/tab/inquiry")
	public String loadInquiryTab(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
		String userId = userDetails.getUser().getUserId();
		List<InquiryDto> inquiries = myPageService.getMyInquiries(userId);
		model.addAttribute("inquiries", inquiries);
		return "mypage/mypage_inquiry";
	}

	// 문의 작성 처리
	@PostMapping("/inquiry/write")
	@ResponseBody
	public String writeInquiry(@AuthenticationPrincipal CustomUserDetails userDetails,
			@RequestBody InquiryDto inquiryDto) {
		String userId = userDetails.getUser().getUserId();
		inquiryDto.setUserId(userId);
		boolean result = myPageService.insertInquiry(inquiryDto);
		return result ? "success" : "fail";
	}
	
	// 문의글 삭제
	@PostMapping("/inquiry/delete")
	@ResponseBody
	public String deleteInquiry(@AuthenticationPrincipal CustomUserDetails userDetails,
	                            @RequestParam("inquiryId") int inquiryId) {
	    String userId = userDetails.getUser().getUserId();
	    boolean result = myPageService.deleteInquiry(userId, inquiryId);
	    return result ? "success" : "fail";
	}
}
