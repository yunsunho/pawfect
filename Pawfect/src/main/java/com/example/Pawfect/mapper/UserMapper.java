package com.example.Pawfect.mapper;

import com.example.Pawfect.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    // 1. 로그인 - 아이디로 유저 조회
    UserDto findByUserId(String userId);

    // 2. 회원가입 - 유저 삽입
    void insertUser(UserDto user);

    // 3. 이메일 인증 시 이메일 중복 체크
    int countByEmail(String email);

    // 4. 아이디 찾기 - 이메일로 userId 조회
    String findUserIdByEmail(String email);

    // 5. 비밀번호 재설정 - 이메일 + userId 일치 시 비번 변경
    void updatePassword(@Param("userId") String userId, @Param("pwd") String pwd);
}
