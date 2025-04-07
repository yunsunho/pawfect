package com.example.Pawfect.config;

import com.example.Pawfect.auth.CustomUserDetailsService;
import com.example.Pawfect.auth.LoginFailureHandler;
import com.example.Pawfect.auth.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	private LoginFailureHandler loginFailureHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", "/main",
                    "/loginForm", "/signupForm",
                    "/findIdForm", "/findPwForm",
                    "/css/**", "/js/**", "/images/**"
                ).permitAll()
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers(
                    "/mypage/**",
                    "/board/write", "/board/comment", "/board/like", 
                    "/travel/bookmark/**", 
                    "/travel/review/**"   
                ).authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(login -> login
                .loginPage("/loginForm")           
                .loginProcessingUrl("/user/login")      
                .usernameParameter("userId")            
                .passwordParameter("pwd")
                .successHandler(loginSuccessHandler) 
                .failureHandler(loginFailureHandler) 
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/main")            
                .invalidateHttpSession(true)           
            );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
