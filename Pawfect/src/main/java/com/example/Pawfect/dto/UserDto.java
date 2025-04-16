package com.example.Pawfect.dto;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	private String userId;
	private String userName;
	private String userTel;
	private String userNickname;
	private String userImage;
	private String userImageOrigin;
	private Timestamp userRegdate;
	private String petName;
	private int petType;
	private String pwd;
	private String email;

	private boolean admin;
	private String userStatus; // ACTIVE, BANNED, WITHDRAWN
	
	private String formattedDate;
}