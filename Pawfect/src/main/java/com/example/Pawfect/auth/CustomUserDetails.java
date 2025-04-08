package com.example.Pawfect.auth;

import com.example.Pawfect.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

	private final UserDto user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (user.isAdmin()) {
			return Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
		}
		return Collections.emptyList();
	}

	@Override
	public String getPassword() {
		return user.getPwd();
	}

	@Override
	public String getUsername() {
		return user.getUserId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true; // 계정 만료 기능은 사용 안함
	}

	@Override
	public boolean isAccountNonLocked() {
		return true; // 계정 잠금 기능은 사용 안함
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; // 비밀번호 만료 기능은 사용 안함
	}

	@Override
	public boolean isEnabled() {
		return "ACTIVE".equalsIgnoreCase(user.getUserStatus());
	}
}