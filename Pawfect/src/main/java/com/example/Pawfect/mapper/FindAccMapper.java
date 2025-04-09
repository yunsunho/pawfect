package com.example.Pawfect.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FindAccMapper {
    // 아이디 조회 (이름 + 이메일)
    String findUserIdByNameAndEmail(@Param("userName") String userName, @Param("email") String email);
}
