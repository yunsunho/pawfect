package com.example.Pawfect.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        String errorMessage = "로그인에 실패했습니다.";

        if (exception instanceof BadCredentialsException) {
            errorMessage = "아이디 또는 비밀번호가 틀렸습니다.";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "존재하지 않는 아이디입니다.";
        } else if (exception instanceof DisabledException) {
            errorMessage = exception.getMessage();
        }

        // 아이디 또는 비밀번호를 입력하지 않은 경우
        if (request.getParameter("userId") == null || request.getParameter("userId").isBlank()
         || request.getParameter("pwd") == null || request.getParameter("pwd").isBlank()) {
            errorMessage = "아이디와 비밀번호를 모두 입력해주세요.";
        }

        response.sendRedirect("/user/loginForm?error=true&message=" + java.net.URLEncoder.encode(errorMessage, "UTF-8"));
    }
}
