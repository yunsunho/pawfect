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
}
