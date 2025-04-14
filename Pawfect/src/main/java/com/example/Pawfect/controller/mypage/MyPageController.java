package com.example.Pawfect.controller.mypage;

import com.example.Pawfect.auth.CustomUserDetails;
import com.example.Pawfect.dto.*;
import com.example.Pawfect.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

	private final MyPageService myPageService;

	@GetMapping("")
	public String showMyPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
		if (userDetails == null) {
			return "redirect:/loginForm";
		}

		String userId = userDetails.getUser().getUserId();

		MyPageUserDto user = myPageService.getUserSummary(userId);
		List<BookmarkDto> bookmarks = myPageService.getBookmarks(userId);
		List<PostDto> posts = myPageService.getMyPosts(userId);
		List<CommentDto> comments = myPageService.getMyComments(userId);
		List<InquiryDto> inquiries = myPageService.getMyInquiries(userId);

		model.addAttribute("user", user);
		model.addAttribute("bookmarks", bookmarks);
		model.addAttribute("posts", posts);
		model.addAttribute("comments", comments);
		model.addAttribute("inquiries", inquiries);

		return "mypage/mypage";
	}

	@GetMapping("/tab/profile")
	public String loadProfileTab(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
		if (userDetails == null) {
			return "redirect:/loginForm";
		}

		String userId = userDetails.getUser().getUserId();

		MyPageUserDto user = myPageService.getUserSummary(userId);
		boolean canEditNickname = myPageService.canEditNickname(userId);

		model.addAttribute("user", user);
		model.addAttribute("canEditNickname", canEditNickname);
		model.addAttribute("now", new java.util.Date());

		return "mypage/mypage_profile";
	}

	@GetMapping("/tab/bookmark")
	public String loadBookmarkTab(@AuthenticationPrincipal CustomUserDetails userDetails, Model model,
			@RequestParam(defaultValue = "1") int page) {
		if (userDetails == null) {
			return "redirect:/loginForm";
		}

		String userId = userDetails.getUser().getUserId();
		int pageSize = 9;
		int offset = (page - 1) * pageSize;

		// 전체 북마크 수
		int totalCount = myPageService.getBookmarkCount(userId);

		// 페이징된 북마크 리스트
		List<BookmarkDto> bookmarks = myPageService.getBookmarksPaged(userId, offset, pageSize);

		int totalPages = (int) Math.ceil((double) totalCount / pageSize);

		model.addAttribute("bookmarks", bookmarks);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totalCount", totalCount);
		return "mypage/mypage_bookmark";
	}
}
