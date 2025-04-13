package com.example.Pawfect.mapper;

import com.example.Pawfect.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageMapper {
	// 마이페이지 사용자 정보 조회
	MyPageUserDto getUserSummary(String userId);

	// 북마크 리스트
	List<BookmarkDto> getBookmarks(String userId);

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
	int updateProfileImage(String userId, String imagePath);
	
	// 내 정보 업데이트 메서드
	int updateUserInfo(InfoUpdateDto dto);

}
