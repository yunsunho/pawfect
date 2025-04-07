package com.example.Pawfect.controller.user;

import com.example.Pawfect.dto.UserDto;
import com.example.Pawfect.service.UserService;
import com.example.Pawfect.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class SignupController {
    private final UserService userService;
    private final MailService mailService;

    // 1. 회원가입 폼
    @GetMapping("/signupForm")
    public String signupForm() {
        return "user/signupForm";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(@ModelAttribute UserDto user, Model model) {
        // 필수 항목 검사
        if (user.getUserId() == null || user.getUserId().isBlank() ||
            user.getPwd() == null || user.getPwd().isBlank() ||
            user.getUserName() == null || user.getUserName().isBlank() ||
            user.getEmail() == null || user.getEmail().isBlank()) {

            model.addAttribute("error", "모든 필수 정보를 입력해주세요.");
            return "user/signupForm"; // 실패 시 폼으로 돌아감
        }

        // 아이디 중복 확인
        if (userService.isUserIdDuplicated(user.getUserId())) {
            model.addAttribute("error", "이미 사용 중인 아이디입니다.");
            return "user/signupForm"; // 실패 시 폼으로 돌아감
        }

        // 이메일 중복 확인
        if (userService.isEmailDuplicated(user.getEmail())) {
            model.addAttribute("error", "이미 사용 중인 이메일입니다.");
            return "user/signupForm"; // 실패 시 폼으로 돌아감
        }

        // 이메일 인증 여부 확인
        if (!mailService.isEmailVerified(user.getEmail())) {
            model.addAttribute("error", "이메일 인증을 완료해야 합니다.");
            return "user/signupForm"; // 실패 시 폼으로 돌아감
        }

        // 회원가입 처리
        user.setUserStatus("ACTIVE");
        user.setAdmin(false);
        userService.registerUser(user);

        // 회원가입 성공 후 리다이렉트
        return "redirect:/signupSuccess"; // signupSuccess 페이지로 리다이렉트
    }

    // 아이디 중복 확인
    @GetMapping("/checkId")
    @ResponseBody
    public String checkId(@RequestParam("userId") String userId) {
        boolean isDuplicate = userService.isUserIdDuplicated(userId);
        return isDuplicate ? "이미 사용 중인 아이디입니다." : "사용 가능한 아이디입니다.";
    }

    // 이메일 인증 전송
    @GetMapping("/sendAuthEmail")
    @ResponseBody
    public String sendAuthEmail(@RequestParam("email") String email) {
        mailService.sendVerificationEmail(email);
        return "📧 인증 이메일이 전송되었습니다. 메일함을 확인해주세요!";
    }

    // 인증 코드 확인 메서드
    @PostMapping("/verifyCode")
    @ResponseBody
    public String verifyEmailCode(@RequestParam("email") String email,
                                  @RequestParam("code") String inputCode) {
        // 이메일 인증 처리 로직 (예: 입력한 코드가 맞는지 확인)
        return "success";
    }
}
