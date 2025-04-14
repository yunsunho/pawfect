package com.example.Pawfect.service;

import com.example.Pawfect.dto.*;
import com.example.Pawfect.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
	private final MyPageMapper myPageMapper;

	// 사용자 기본 정보 조회
	public MyPageUserDto getUserSummary(String userId) {
		return myPageMapper.getUserSummary(userId);
	}

	// 북마크 목록
	public List<BookmarkDto> getBookmarks(String userId) {
		return myPageMapper.getBookmarks(userId);
	}

	// 내가 쓴 게시글
	public List<PostDto> getMyPosts(String userId) {
		return myPageMapper.getMyPosts(userId);
	}

	// 내가 쓴 댓글
	public List<CommentDto> getMyComments(String userId) {
		return myPageMapper.getMyComments(userId);
	}

	// 내가 보낸 문의
	public List<InquiryDto> getMyInquiries(String userId) {
		return myPageMapper.getMyInquiries(userId);
	}

	// 닉네임 + nickResetAt + 펫 정보 같이 수정
	public boolean updateProfile(ProfileUpdateDto dto) {
		int nicknameResult = myPageMapper.updateNickname(dto);
		int petResult = myPageMapper.updatePetInfo(dto);
		return nicknameResult > 0 || petResult > 0;
	}

	// 펫 정보만 수정
	public boolean updatePetOnly(ProfileUpdateDto dto) {
		return myPageMapper.updatePetInfo(dto) > 0;
	}

	// 닉네임 변경 가능 여부 (30일 제한)
	public boolean canEditNickname(String userId) {
		return myPageMapper.canEditNickname(userId);
	}

	// 프로필 이미지 경로 업데이트
	public boolean updateProfileImage(String userId, String imagePath) {
		return myPageMapper.updateProfileImage(userId, imagePath) > 0;
	}

	public boolean deleteProfileImage(String userId) {
		String defaultPath = "/images/default_profile.jpg";
		return myPageMapper.updateProfileImage(userId, defaultPath) > 0;
	}

	// 이메일/전화번호 정보 수정
	public boolean updateUserInfo(InfoUpdateDto dto) {
		return myPageMapper.updateUserInfo(dto) > 0;
	}

	// 비밀번호 일치 확인 (현재 비밀번호 검증용)
	public boolean checkPwdMatch(String userId, String inputPwd) {
		String encryptedPwd = myPageMapper.getPwdByUserId(userId);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(inputPwd, encryptedPwd);
	}

	// 비밀번호 업데이트
	public boolean updatePwd(String userId, String newPwd) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPwd = encoder.encode(newPwd);
		return myPageMapper.updatePwd(userId, encodedPwd) > 0;
	}
}
