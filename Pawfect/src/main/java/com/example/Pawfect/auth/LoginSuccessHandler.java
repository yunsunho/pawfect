package com.example.Pawfect.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String redirectUrl = "/main";
		HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
		DefaultSavedRequest savedRequest = (DefaultSavedRequest) requestCache.getRequest(request, response);

		if (savedRequest != null) {
			redirectUrl = savedRequest.getRedirectUrl();
		}

		response.sendRedirect(redirectUrl);
	}
}
