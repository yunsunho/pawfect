package com.example.Pawfect.controller.user;

import com.example.Pawfect.dto.FindIdDto;
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
        return "user/findIdForm"; // WEB-INF/views/user/findIdForm.jsp
    }

    // 아이디 찾기 처리
    @PostMapping("/findId")
    public String processFindId(@ModelAttribute FindIdDto dto, Model model) {
        boolean result = findAccService.sendUserIdByEmail(dto);

        if (result) {
            model.addAttribute("email", dto.getEmail());
            return "user/findIdResult"; // 성공 페이지
        } else {
            model.addAttribute("error", "회원 조회 결과가 없습니다.");
            return "user/findIdForm"; // 실패 시 다시 폼으로 (에러 메시지 포함)
        }
    }
}
