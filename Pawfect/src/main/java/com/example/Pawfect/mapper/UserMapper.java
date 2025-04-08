package com.example.Pawfect.mapper;

import com.example.Pawfect.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    // 로그인 - 아이디로 유저 조회
    UserDto findByUserId(String userId);

    // 회원가입 - 유저 삽입
    void insertUser(UserDto user);

    // 아이디 중복 체크
    int countByUserId(String userId);
    
    // 이메일 인증 시 이메일 중복 체크
    int countByEmail(String email);

    // 이메일 인증 상태 조회
    boolean isEmailVerified(String email);

    // 이메일 인증 상태 업데이트
    void updateEmailVerifiedStatus(@Param("email") String email, @Param("isVerified") boolean isVerified);
}
