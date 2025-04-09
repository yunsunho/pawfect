package com.example.Pawfect.service;

import com.example.Pawfect.dto.FindIdDto;
import com.example.Pawfect.mapper.FindAccMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAccService {

    private final FindAccMapper findAccMapper;
    private final EmailVeriService emailVeriService;

    // 아이디 찾기
    public boolean sendUserIdByEmail(FindIdDto dto) {
        String userId = findAccMapper.findUserIdByNameAndEmail(dto.getUserName(), dto.getEmail());

        if (userId == null) {
            return false; // 회원 조회 실패
        }

        // 이메일로 아이디 전송
        String subject = "[Pawfect Tour] 아이디 찾기 안내";
        String content = """
                <div style='font-family: Arial, sans-serif;'>
                    <h2>Pawfect Tour 아이디 찾기 결과</h2>
                    <p>회원님의 아이디는 다음과 같습니다 :</p>
                    <h3 style='color: #ffbf00;'>""" + userId + "</h3>" 
                    + "<p>※ 타인에게 노출되지 않도록 주의해주세요.</p></div>";

        emailVeriService.sendMail(dto.getEmail(), subject, content);
        
        return true;
    }
}
