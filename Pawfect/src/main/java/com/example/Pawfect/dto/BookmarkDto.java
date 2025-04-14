package com.example.Pawfect.dto;

import lombok.Data;

@Data
public class BookmarkDto {
	private String userId;
	private int contentId;
	private int contentTypeId;
	private String title;
	private String firstimage;
	private float mapX;
	private float mapY;
	private String addr1;
}
