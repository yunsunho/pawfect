package com.example.Pawfect.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PwdUpdateDto {
	private String currentPwd;
	private String newPwd;
	private String confirmPwd;
	private String userId;
}
