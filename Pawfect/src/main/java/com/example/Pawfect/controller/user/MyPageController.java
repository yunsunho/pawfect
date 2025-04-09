package com.example.Pawfect.controller.user;

import com.example.Pawfect.dto.*;
import com.example.Pawfect.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyPageController {

	private final MyPageService myPageService;

	@GetMapping("/mypage")
	public String showMyPage(Model model, HttpSession session) {
		String userId = (String) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/loginForm";
		}

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

		return "user/mypage";
	}
}
