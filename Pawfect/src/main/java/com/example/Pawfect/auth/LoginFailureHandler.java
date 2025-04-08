package com.example.Pawfect.auth;

import jakarta.servlet.ServletException;
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

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		String error = "unknown";

		if (exception instanceof UsernameNotFoundException) {
			error = "userNotFound";
		} else if (exception instanceof BadCredentialsException) {
			error = "invalidCredentials";
		} else if (exception instanceof DisabledException) {
			String reason = exception.getMessage();
			if ("정지된 계정입니다.".equals(reason)) {
				error = "accountBanned";
			} else if ("탈퇴된 계정입니다.".equals(reason)) {
				error = "accountWithdrawn";
			} else {
				error = "disabled";
			}
		} else if (exception instanceof LockedException) {
			error = "accountLocked";
		}

		if (request.getParameter("userId") == null || request.getParameter("userId").trim().isEmpty()
				|| request.getParameter("pwd") == null || request.getParameter("pwd").trim().isEmpty()) {
			response.sendRedirect("/loginResult?status=failure&error=emptyFields");
			return;
		}

		response.sendRedirect("/loginResult?status=failure&error=" + error);
	}
}
