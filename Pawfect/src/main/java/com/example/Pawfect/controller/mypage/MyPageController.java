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

		return "mypage/mypage_profile";
	}
}
