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
	private final EmailVeriService emailVeriService;

	// 1. 아이디로 유저 조회
	public UserDto getUserById(String userId) {
		return userMapper.findByUserId(userId);
	}

	// 2. 사용자 인증 (아이디 + 비밀번호) -> 인증 성공 시 UserDto 반환
	public UserDto authenticateAndGetUser(String userId, String pwd) {
		UserDto user = userMapper.findByUserId(userId);
		if (user != null && passwordEncoder.matches(pwd, user.getPwd())) {
			return user;
		}
		return null;
	}

	// 3. 비밀번호만 검증 (아이디는 이미 확인된 경우)
	public boolean isPasswordCorrect(UserDto user, String rawPwd) {
		return passwordEncoder.matches(rawPwd, user.getPwd());
	}

	// 4. 회원가입
	public void registerUser(UserDto user) {
		boolean isEmailVerified = emailVeriService.isEmailVerified(user.getEmail());
		if (!isEmailVerified) {
			throw new RuntimeException("이메일 인증이 완료되지 않았습니다.");
		}

		String encodedPwd = passwordEncoder.encode(user.getPwd());
		user.setPwd(encodedPwd);

		userMapper.insertUser(user);
	}

	// 5. 아이디 중복 체크
	public boolean isUserIdDuplicated(String userId) {
		return userMapper.countByUserId(userId) > 0;
	}

	// 6. 이메일 중복 체크
	public boolean isEmailDuplicated(String email) {
		return userMapper.countByEmail(email) > 0;
	}
}
