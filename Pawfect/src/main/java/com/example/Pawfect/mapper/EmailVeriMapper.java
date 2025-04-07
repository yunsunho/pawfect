package com.example.Pawfect.mapper;

import com.example.Pawfect.dto.EmailVeriDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailVeriMapper {

    // 이메일로 인증 정보 조회
    EmailVeriDto findByEmail(String email);

    // 인증 코드 저장 또는 갱신
    void saveOrUpdateCode(EmailVeriDto dto);

    // 인증 성공 처리 (isVerified = true)
    void updateVerifiedStatus(String email, boolean isVerified);
}
