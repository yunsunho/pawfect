package com.example.Pawfect.mapper;

import com.example.Pawfect.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyPageMapper {
	// 마이페이지 사용자 정보 조회
	MyPageUserDto getUserSummary(String userId);

	// 북마크 리스트
	List<BookmarkDto> getBookmarks(String userId);

	// 전체 북마크 개수 조회
	int getBookmarkCount(String userId);

	// 페이징된 북마크 리스트 조회
	List<BookmarkDto> getBookmarksPaged(@Param("userId") String userId, @Param("offset") int offset,
			@Param("limit") int limit);

	// 내가 쓴 게시글
	List<PostDto> getMyPosts(String userId);

	// 내가 쓴 댓글
	List<CommentDto> getMyComments(String userId);

	// 내가 보낸 문의
	List<InquiryDto> getMyInquiries(String userId);

	// 닉네임(nickResetAt 포함)만 수정
	int updateNickname(ProfileUpdateDto dto);

	// 반려동물 정보만 수정
	int updatePetInfo(ProfileUpdateDto dto);

	// 닉네임 변경 가능 여부 (30일 제한)
	boolean canEditNickname(String userId);

	// 프로필 이미지 경로 업데이트
	int updateProfileImage(@Param("userId") String userId, @Param("imagePath") String imagePath,
			@Param("originalFilename") String originalFilename);

	// 내 정보 업데이트
	int updateUserInfo(InfoUpdateDto dto);

	// 현재 암호화된 비밀번호 가져오기
	String getPwdByUserId(String userId);

	// 비밀번호 업데이트
	int updatePwd(@Param("userId") String userId, @Param("encodedPwd") String encodedPwd);

	// 1:1 문의글 작성
	int insertInquiry(InquiryDto inquiryDto);

	// 문의글 삭제
	int deleteInquiry(@Param("userId") String userId, @Param("inquiryId") int inquiryId);

	// 전체 문의 개수 조회
	int getInquiryCount(String userId);

	// 페이징된 문의 리스트 조회
	List<InquiryDto> getMyInquiriesPaged(@Param("userId") String userId, @Param("offset") int offset,
			@Param("limit") int limit);
}
