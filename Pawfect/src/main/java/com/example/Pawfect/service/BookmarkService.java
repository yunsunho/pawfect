package com.example.Pawfect.service;

import com.example.Pawfect.dto.BookmarkDto;
import com.example.Pawfect.mapper.BookmarkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {

	private final BookmarkMapper bookmarkMapper;

	public void save(BookmarkDto dto) {
		bookmarkMapper.insertBookmark(dto);
	}

	public void delete(BookmarkDto dto) {
		bookmarkMapper.deleteBookmark(dto);
	}

	public boolean isBookmarked(BookmarkDto dto) {
		return bookmarkMapper.countBookmark(dto) > 0;
	}
}
