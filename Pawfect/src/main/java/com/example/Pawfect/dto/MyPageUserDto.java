package com.example.Pawfect.dto;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPageUserDto {
    private String userId;
    private String userNickname;
    private String userImage;
    private Timestamp userRegdate;
}
