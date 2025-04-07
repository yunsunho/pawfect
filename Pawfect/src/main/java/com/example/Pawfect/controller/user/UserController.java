package com.example.Pawfect.controller.user;

import com.example.Pawfect.dto.UserDto;
import com.example.Pawfect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    // 1. 로그인
    @GetMapping("/loginForm")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "message", required = false) String message,
                            Model model) {
        model.addAttribute("error", error);
        model.addAttribute("message", message);
        return "user/loginForm";
    }

    // 2. 회원가입
    @GetMapping("/signupForm")
    public String signupForm() {
        return "user/signupForm";
    }

    // 3. 아이디 찾기
    @GetMapping("/findIdForm")
    public String findIdForm() {
        return "user/findIdForm";
    }

    // 4. 비밀번호 찾기
    @GetMapping("/findPwForm")
    public String findPwForm() {
        return "user/findPwForm";
    }

    // 5. 아이디/비번 찾기 결과
    @GetMapping("/findPro")
    public String findPro() {
        return "user/findPro";
    }

    // 6. 비밀번호 재설정 (이메일 링크 통해 접근)
    @GetMapping("/resetPassword")
    public String resetPasswordForm(@RequestParam("userId") String userId, Model model) {
        model.addAttribute("userId", userId);
        return "user/resetPassword";
    }

    // 7. 회원가입 처리
    @PostMapping("/signup")
    public String signup(@ModelAttribute UserDto user, Model model) {
        user.setUserStatus("ACTIVE");
        user.setAdmin(false);
        userService.registerUser(user);
        return "redirect:/user/loginForm?message=회원가입이+완료되었습니다.";
    }

    // 8. 비밀번호 재설정 처리
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("userId") String userId,
                                 @RequestParam("newPassword") String newPassword) {
        userService.resetPassword(userId, newPassword);
        return "redirect:/user/loginForm?message=비밀번호가+변경되었습니다.";
    }
}
