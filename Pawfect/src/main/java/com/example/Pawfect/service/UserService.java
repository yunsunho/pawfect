package com.example.Pawfect.service;

import com.example.Pawfect.dto.UserDto;
import com.example.Pawfect.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    // 1. 로그인 - 아이디로 유저 조회
    public UserDto getUserById(String userId) {
        return userMapper.findByUserId(userId);
    }

    // 2. 회원가입
    public void registerUser(UserDto user) {
        // 비밀번호 암호화
        String encodedPwd = passwordEncoder.encode(user.getPwd());
        user.setPwd(encodedPwd);
        userMapper.insertUser(user);
    }
    
    // 3. 아이디 중복 체크
    public boolean isUserIdDuplicated(String userId) {
        return userMapper.countByUserId(userId) > 0;
    }
    
    // 4. 이메일 중복 체크
    public boolean isEmailDuplicated(String email) {
        return userMapper.countByEmail(email) > 0;
    }
    
    // 5. 아이디 찾기 (이메일로 userId 반환)
    public String findUserIdByEmail(String email) {
        return userMapper.findUserIdByEmail(email);
    }

    // 6. 비밀번호 재설정
    public void resetPassword(String userId, String newPassword) {
        String encodedPwd = passwordEncoder.encode(newPassword);
        userMapper.updatePassword(userId, encodedPwd);
    }
}
