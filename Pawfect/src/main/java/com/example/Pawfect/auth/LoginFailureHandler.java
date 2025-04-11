package com.example.Pawfect.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException {

		String message = "로그인 실패! 다시 시도해주세요.";

		if (exception instanceof UsernameNotFoundException) {
			message = "존재하지 않는 아이디입니다. 다시 확인해주세요.";
		} else if (exception instanceof BadCredentialsException) {
			message = "일치하지 않는 비밀번호입니다. 다시 확인해주세요.";
		} else if (exception instanceof DisabledException) {
			String reason = exception.getMessage();
			if ("정지된 계정입니다.".equals(reason)) {
				message = "정지된 계정입니다.";
			} else if ("탈퇴된 계정입니다.".equals(reason)) {
				message = "탈퇴한 계정입니다.";
			} else {
				message = "비활성화된 계정입니다.";
			}
		} else if (exception instanceof LockedException) {
			message = "잠긴 계정입니다.";
		}

		if (request.getParameter("userId") == null || request.getParameter("userId").trim().isEmpty()
				|| request.getParameter("pwd") == null || request.getParameter("pwd").trim().isEmpty()) {
			message = "아이디 또는 비밀번호를 입력해주세요.";
		}

		String encodedMessage = URLEncoder.encode(message, "UTF-8");
		response.sendRedirect("/loginForm?error=true&message=" + encodedMessage);
	}

}
