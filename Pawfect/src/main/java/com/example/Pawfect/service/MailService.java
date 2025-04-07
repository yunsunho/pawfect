package com.example.Pawfect.service;

import com.example.Pawfect.dto.EmailVeriDto;
import com.example.Pawfect.mapper.EmailVeriMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final EmailVeriMapper emailVeriMapper;

    // 인증 메일 전송 + DB 저장
    public void sendVerificationEmail(String toEmail) {
        String authCode = createAuthCode();
        String subject = "[Pawfect Tour] 이메일 인증을 완료해주세요.";

        String content = """
            <div style="font-family: Arial, sans-serif;">
              <h2>Pawfect 회원가입 인증</h2>
              <p>아래 인증 코드를 입력해주세요:</p>
              <h3 style="color: #f4c542;">""" + authCode + "</h3>" +
            "<p>※ 인증 코드는 5분 후 만료됩니다.</p></div>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 실패: " + e.getMessage());
        }

        // DB에 인증코드 저장 (insert or update)
        EmailVeriDto dto = new EmailVeriDto();
        dto.setEmail(toEmail);
        dto.setCode(authCode);
        dto.setVerified(false);
        dto.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        emailVeriMapper.saveOrUpdateCode(dto);
    }

    // 인증 여부 + 유효 시간(5분 이내) 확인
    public boolean isEmailVerified(String email) {
        EmailVeriDto verification = emailVeriMapper.findByEmail(email);

        if (verification == null || !verification.isVerified()) {
            return false;
        }

        long minutes = Duration.between(verification.getCreatedAt().toInstant(), Instant.now()).toMinutes();
        return minutes <= 5;
    }

    // 인증코드 생성 (6자리 숫자)
    private String createAuthCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 100000 ~ 999999
        return String.valueOf(code);
    }
}
