package com.example.Pawfect.auth;

import com.example.Pawfect.dto.UserDto;
import com.example.Pawfect.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 아이디가 DB에 존재하지 않을 경우
        UserDto user = userMapper.findByUserId(userId);
        if (user == null) {
            throw new UsernameNotFoundException("존재하지 않는 아이디입니다.");
        }

        // 정지된 계정
        if ("BANNED".equalsIgnoreCase(user.getUserStatus())) {
            throw new DisabledException("정지된 계정입니다.");
        }

        // 탈퇴된 계정
        if ("WITHDRAWN".equalsIgnoreCase(user.getUserStatus())) {
            throw new DisabledException("탈퇴된 계정입니다.");
        }

        return new CustomUserDetails(user);
    }
}
