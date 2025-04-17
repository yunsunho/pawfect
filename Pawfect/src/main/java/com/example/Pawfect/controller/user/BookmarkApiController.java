package com.example.Pawfect.controller.user;

import com.example.Pawfect.dto.BookmarkDto;
import com.example.Pawfect.service.BookmarkService;
import com.example.Pawfect.auth.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class BookmarkApiController {

    private final BookmarkService bookmarkService;

    @GetMapping("/bookmarks")
    public List<BookmarkDto> getUserBookmarks(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        return bookmarkService.getBookmarksByUserId(userDetails.getUser().getUserId());
    }
}
