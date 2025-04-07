package com.example.Pawfect.controller.user;

import com.example.Pawfect.dto.UserDto;
import com.example.Pawfect.dto.EmailVeriDto; 
import com.example.Pawfect.mapper.EmailVeriMapper; 
import com.example.Pawfect.service.UserService;
import com.example.Pawfect.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final MailService mailService;
    private final EmailVeriMapper emailVeriMapper;

    // 1. 로그인 폼
    @GetMapping("/loginForm")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "message", required = false) String message,
                            Model model) {
        model.addAttribute("error", error);
        model.addAttribute("message", message);
        return "user/loginForm";
    }

    // 2. 회원가입 폼
    @GetMapping("/signupForm")
    public String signupForm() {
        return "user/signupForm";
    }

    // 3. 회원가입 처리
    @PostMapping("/signup")
    public String signup(@ModelAttribute UserDto user, Model model) {
        if (user.getUserId() == null || user.getUserId().isBlank() ||
            user.getPwd() == null || user.getPwd().isBlank() ||
            user.getUserName() == null || user.getUserName().isBlank() ||
            user.getEmail() == null || user.getEmail().isBlank()) {

            model.addAttribute("error", "모든 필수 정보를 입력해주세요.");
            return "user/signupForm";
        }

        if (userService.isUserIdDuplicated(user.getUserId())) {
            model.addAttribute("error", "이미 사용 중인 아이디입니다.");
            return "user/signupForm";
        }

        if (!mailService.isEmailVerified(user.getEmail())) {
            model.addAttribute("error", "이메일 인증을 완료해야 합니다.");
            return "user/signupForm";
        }

        user.setUserStatus("ACTIVE");
        user.setAdmin(false);
        userService.registerUser(user);

        return "redirect:/user/loginForm?message=회원가입이+완료되었습니다.";
    }

    // 4. 아이디 중복확인
    @GetMapping("/checkId")
    @ResponseBody
    public String checkId(@RequestParam("userId") String userId) {
        boolean isDuplicate = userService.isUserIdDuplicated(userId);
        return isDuplicate ? "이미 사용 중인 아이디입니다." : "사용 가능한 아이디입니다.";
    }

    // 5. 이메일 인증 전송
    @GetMapping("/sendAuthEmail")
    @ResponseBody
    public String sendAuthEmail(@RequestParam("email") String email) {
        mailService.sendVerificationEmail(email);
        return "📧 인증 이메일이 전송되었습니다. 메일함을 확인해주세요!";
    }

    // 인증코드 확인 메서드 추가
    @PostMapping("/verifyCode")
    @ResponseBody
    public String verifyEmailCode(@RequestParam("email") String email,
                                  @RequestParam("code") String inputCode) {
        EmailVeriDto verification = emailVeriMapper.findByEmail(email);

        if (verification == null) {
            return "이메일 인증 정보가 존재하지 않습니다.";
        }

        if (!verification.getCode().equals(inputCode)) {
            return "인증 코드가 일치하지 않습니다.";
        }

        long minutes = Duration.between(verification.getCreatedAt().toInstant(), Instant.now()).toMinutes();
        if (minutes > 5) {
            return "인증 코드가 만료되었습니다.";
        }

        emailVeriMapper.updateVerifiedStatus(email, true);
        return "success";
    }

    // 6. 아이디 찾기 폼
    @GetMapping("/findIdForm")
    public String findIdForm() {
        return "user/findIdForm";
    }

    // 7. 비밀번호 찾기 폼
    @GetMapping("/findPwForm")
    public String findPwForm() {
        return "user/findPwForm";
    }

    // 8. 아이디/비번 찾기 결과
    @GetMapping("/findPro")
    public String findPro() {
        return "user/findPro";
    }

    // 9. 비밀번호 재설정 폼
    @GetMapping("/resetPassword")
    public String resetPasswordForm(@RequestParam("userId") String userId, Model model) {
        model.addAttribute("userId", userId);
        return "user/resetPassword";
    }

    // 10. 비밀번호 재설정 처리
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("userId") String userId,
                                 @RequestParam("newPassword") String newPassword) {
        userService.resetPassword(userId, newPassword);
        return "redirect:/user/loginForm?message=비밀번호가+변경되었습니다.";
    }

    // 11. 이메일 전송 테스트 (임시)
    @GetMapping("/testEmail")
    @ResponseBody
    public String testEmail(@RequestParam("email") String email) {
        mailService.sendVerificationEmail(email);
        return "이메일이 전송되었습니다: " + email;
    }
}
