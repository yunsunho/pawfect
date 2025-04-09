package com.example.Pawfect.controller.user;

import com.example.Pawfect.dto.FindIdDto;
import com.example.Pawfect.dto.FindPwdDto;
import com.example.Pawfect.service.FindAccService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class FindAccController {

	private final FindAccService findAccService;

	// 아이디 찾기 폼
	@GetMapping("/findId")
	public String showFindIdForm() {
		return "user/findIdForm";
	}

	// 아이디 찾기 처리
	@PostMapping("/findId")
	public String processFindId(@ModelAttribute FindIdDto dto, Model model) {
		boolean result = findAccService.sendUserIdByEmail(dto);

		if (result) {
			model.addAttribute("email", dto.getEmail());
			return "user/findIdResult";
		} else {
			model.addAttribute("error", "회원 조회 결과가 없습니다.");
			return "user/findIdForm";
		}
	}

	// 비밀번호 찾기 폼
	@GetMapping("/findPwd")
	public String showFindPwdForm() {
		return "user/findPwdForm"; // user/findPwdForm.jsp
	}

	// 비밀번호 찾기 처리
	@PostMapping("/findPwd")
	public String processFindPwd(@ModelAttribute FindPwdDto dto, Model model) {
		int result = findAccService.resetPasswordAndSendEmail(dto);

		if (result == 0) {
			model.addAttribute("email", dto.getEmail());
			return "user/findPwdResult";
		} else if (result == 1) {
			model.addAttribute("error", "최근 5분 이내에 비밀번호 찾기 요청이 처리되었습니다.<br>메일함을 확인해 주세요!");
			return "user/findPwdForm";
		} else { // result == 2
			model.addAttribute("error", "회원 정보가 일치하지 않습니다.");
			return "user/findPwdForm";
		}
	}

}
