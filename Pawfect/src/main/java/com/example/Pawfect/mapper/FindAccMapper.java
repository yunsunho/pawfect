package com.example.Pawfect.mapper;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FindAccMapper {
	// 아이디 조회 (이름 + 이메일)
	String findUserIdByNameAndEmail(@Param("userName") String userName, @Param("email") String email);

	// 사용자 정보 일치 확인 (이름 + 아이디 + 이메일)
	int countUserByInfo(@Param("userName") String userName, @Param("userId") String userId,
			@Param("email") String email);

	// 비밀번호 업데이트
	void updatePasswordByUserId(@Param("userId") String userId, @Param("newPwd") String newPwd);

	// 마지막 비밀번호 찾기 요청 시각 조회
	Timestamp getPwdResetAt(@Param("userId") String userId);

	// 비밀번호 찾기 요청 시각 갱신
	void updatePwdResetAt(@Param("userId") String userId, @Param("now") Timestamp now);
}
