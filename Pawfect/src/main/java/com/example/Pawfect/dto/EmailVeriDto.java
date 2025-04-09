package com.example.Pawfect.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailVeriDto {
	private String email;
	private String code;
	private boolean isVerified;
	private Timestamp createdAt;
}