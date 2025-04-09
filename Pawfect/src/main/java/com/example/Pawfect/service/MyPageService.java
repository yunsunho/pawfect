package com.example.Pawfect.service;

import com.example.Pawfect.dto.*;
import com.example.Pawfect.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;
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
}
