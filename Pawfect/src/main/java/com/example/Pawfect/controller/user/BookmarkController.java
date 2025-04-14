package com.example.Pawfect.controller.user;

import com.example.Pawfect.dto.BookmarkDto;
import com.example.Pawfect.service.BookmarkService;
import com.example.Pawfect.auth.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/travel/bookmark")
public class BookmarkController {

	private final BookmarkService bookmarkService;

	@PostMapping("/toggle")
	@ResponseBody
	public String toggleBookmark(@RequestBody BookmarkDto dto,
	                             @AuthenticationPrincipal CustomUserDetails userDetails) {
	    if (userDetails == null) return "redirect:/loginForm";

	    dto.setUserId(userDetails.getUser().getUserId());

	    boolean isBookmarked = bookmarkService.isBookmarked(dto);
	    if (isBookmarked) {
	        bookmarkService.delete(dto);
	        return "deleted";
	    } else {
	        bookmarkService.save(dto);
	        return "saved";
	    }
	}
}

