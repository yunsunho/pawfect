package com.example.Pawfect.service;

import com.example.Pawfect.dto.FindIdDto;
import com.example.Pawfect.dto.FindPwdDto;
import com.example.Pawfect.mapper.FindAccMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindAccService {

	private final FindAccMapper findAccMapper;
	private final EmailVeriService emailVeriService;
	private final BCryptPasswordEncoder passwordEncoder;

	// 아이디 찾기
	public boolean sendUserIdByEmail(FindIdDto dto) {
		String userId = findAccMapper.findUserIdByNameAndEmail(dto.getUserName(), dto.getEmail());

		if (userId == null) {
			return false; // 회원 조회 실패
		}

		String subject = "[Pawfect Tour] 아이디 찾기 안내";
		String content = """
				<div style='font-family: Arial, sans-serif;'>
				    <h2>Pawfect Tour 아이디</h2>
				    <p>회원님의 아이디는 다음과 같습니다 :</p>
				    <h3 style='color: #ffbf00;'>""" + userId + "</h3>" 
				    + "<p>※ 타인에게 노출되지 않도록 주의해주세요.</p></div>";

		emailVeriService.sendMail(dto.getEmail(), subject, content);
		return true;
	}

	// 비밀번호 찾기
	public int resetPasswordAndSendEmail(FindPwdDto dto) {
	    int count = findAccMapper.countUserByInfo(dto.getUserName(), dto.getUserId(), dto.getEmail());
	    if (count == 0) return 2; // 회원 정보 불일치

	    Timestamp lastRequested = findAccMapper.getPwdResetAt(dto.getUserId());
	    if (lastRequested != null) {
	        long minutes = Duration.between(lastRequested.toInstant(), Instant.now()).toMinutes();
	        if (minutes < 5) return 1; // 5분 이내 요청 차단
	    }

	    String tempPassword = generateTempPassword();
	    String encodedPassword = passwordEncoder.encode(tempPassword);
	    findAccMapper.updatePasswordByUserId(dto.getUserId(), encodedPassword);
	    findAccMapper.updatePwdResetAt(dto.getUserId(), Timestamp.from(Instant.now()));

	    // 이메일 전송
	    String subject = "[Pawfect Tour] 비밀번호 찾기 안내";
	    String content = """
	            <div style='font-family: Arial, sans-serif;'>
	                <h2>Pawfect Tour 임시 비밀번호</h2>
	                <p>회원님의 임시 비밀번호는 다음과 같습니다:</p>
	                <h3 style='color: #ff4f4f;'>""" + tempPassword + "</h3>" +
	        "<p>로그인 후 반드시 비밀번호를 변경해 주세요.</p>" +
	        "<p>※ 타인에게 노출되지 않도록 주의해주세요.</p></div>";

	    emailVeriService.sendMail(dto.getEmail(), subject, content);
	    return 0; // 성공
	}
	
	// 임시 비밀번호 생성 (8자리 무작위 문자열)
	private String generateTempPassword() {
		return UUID.randomUUID().toString().substring(0, 8);
	}
}
