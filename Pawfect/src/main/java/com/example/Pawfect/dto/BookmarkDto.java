package com.example.Pawfect.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkDto {
    private int contentId;
    private String title;
    private String addr1;
    private String addr2;
    private String firstImage2;
    private float mapX;
    private float mapY;
    private Timestamp bookmarkRegdate;
}
