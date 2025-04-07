package com.example.Pawfect.controller.user;

import com.example.Pawfect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    // 1. 로그인 폼
    @GetMapping("/loginForm")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "message", required = false) String message,
                            Model model) {
        model.addAttribute("error", error);
        model.addAttribute("message", message);
        return "user/loginForm";
    }
    
    // 1-1. 로그인 처리 (POST 방식 추가)
    @PostMapping("/login")
    public String login(@RequestParam("userId") String userId,
                        @RequestParam("pwd") String pwd, 
                        Model model) {
        if (userService.authenticate(userId, pwd)) {
            return "redirect:/main"; // 성공 시 main 페이지로 리디렉션
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 틀렸습니다.");
            return "user/loginForm"; // 실패 시 로그인 폼으로 돌아감
        }
    }
}
