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

	    String redirectUrl = "/main"; // 기본값

	    // JS에서 저장한 세션 우선 확인
	    String afterLogin = (String) request.getSession().getAttribute("afterLoginRedirect");
	    if (afterLogin != null) {
	        redirectUrl = afterLogin;
	        request.getSession().removeAttribute("afterLoginRedirect");
	    } else {
	        // 없을 경우에만 savedRequest 사용
	        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
	        DefaultSavedRequest savedRequest = (DefaultSavedRequest) requestCache.getRequest(request, response);
	        if (savedRequest != null) {
	            redirectUrl = savedRequest.getRedirectUrl();
	        }
	    }

	    response.sendRedirect(redirectUrl);
	}

}
