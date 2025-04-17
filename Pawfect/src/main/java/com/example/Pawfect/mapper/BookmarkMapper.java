package com.example.Pawfect.mapper;

import com.example.Pawfect.dto.BookmarkDto;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookmarkMapper {
	
	void insertBookmark(BookmarkDto dto);
	void deleteBookmark(BookmarkDto dto);
	int countBookmark(BookmarkDto dto); // 0이면 없음, 1 이상이면 있음
	List<Integer> selectContentIdsByUserId(String userId);	
	int getBookmarkCount(int contentId);
	int countByContentId(int contentId);
	List<Map<String, Object>> selectContentIdsSortedByBookmarkCountAndType(int contentTypeId);
	List<Map<String, Object>> selectContentIdsSortedByBookmarkCountAndArea(String areaCode, String sigunguCode);
	List<BookmarkDto> selectBookmarksByUserId(String userId);

}
