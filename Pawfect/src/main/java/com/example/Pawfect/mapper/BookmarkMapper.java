package com.example.Pawfect.mapper;

import com.example.Pawfect.dto.BookmarkDto;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookmarkMapper {
	
	void insertBookmark(BookmarkDto dto);
	void deleteBookmark(BookmarkDto dto);
	int countBookmark(BookmarkDto dto); // 0이면 없음, 1 이상이면 있음
	List<Integer> selectContentIdsByUserId(String userId);
}
