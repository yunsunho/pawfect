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

	// 전체 북마크 수 조회
	public int getBookmarkCount(String userId) {
		return myPageMapper.getBookmarkCount(userId);
	}

	// 페이징된 북마크 리스트 조회
	public List<BookmarkDto> getBookmarksPaged(String userId, int offset, int limit) {
		return myPageMapper.getBookmarksPaged(userId, offset, limit);
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
	public boolean updateProfileImage(String userId, String imagePath, String originalFilename) {
		return myPageMapper.updateProfileImage(userId, imagePath, originalFilename) > 0;
	}

	public boolean deleteProfileImage(String userId) {
		String defaultPath = "/images/default_profile.jpg";
		return myPageMapper.updateProfileImage(userId, defaultPath, "") > 0;
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

	// 1:1 문의글 작성
	public boolean insertInquiry(InquiryDto inquiryDto) {
		return myPageMapper.insertInquiry(inquiryDto) > 0;
	}

	// 문의글 삭제
	public boolean deleteInquiry(String userId, int inquiryId) {
		return myPageMapper.deleteInquiry(userId, inquiryId) > 0;
	}

	// 전체 문의 개수 조회
	public int getInquiryCount(String userId) {
		return myPageMapper.getInquiryCount(userId);
	}

	// 페이징된 문의 리스트 조회
	public List<InquiryDto> getMyInquiriesPaged(String userId, int offset, int limit) {
		return myPageMapper.getMyInquiriesPaged(userId, offset, limit);
	}
}
