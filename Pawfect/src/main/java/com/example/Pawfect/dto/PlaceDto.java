package com.example.Pawfect.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceDto {
    private int contentId;
    private int contentTypeId;
    private String title;
    private int areaCode;
    private String addr1;
    private String addr2;
    private String firstImage2;
    private float mapX;
    private float mapY;
    private float avgRating;
}