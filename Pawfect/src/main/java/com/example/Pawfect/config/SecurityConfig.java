package com.example.Pawfect.config;

import com.example.Pawfect.auth.CustomUserDetailsService;
import com.example.Pawfect.auth.LoginFailureHandler;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
	private final LoginFailureHandler loginFailureHandler;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	    provider.setUserDetailsService(customUserDetailsService);
	    provider.setPasswordEncoder(passwordEncoder());
	    provider.setHideUserNotFoundExceptions(false);
	    return provider;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(
								"/", "/main", 
								"/loginForm", "/signupForm", 
								"/findIdForm", "/findPwdForm",
								"/css/**", "/js/**", "/images/**").permitAll()
						.requestMatchers("/admin/**").hasAuthority("ADMIN")
						.requestMatchers(
								"/mypage/**", 
								"/board/write", "/board/comment", "/board/like",
								"/travel/bookmark/**", "/travel/review/**").authenticated()
						.anyRequest().permitAll())
				.formLogin(
						login -> login
						.loginPage("/loginForm")
						.loginProcessingUrl("/login")
						.usernameParameter("userId")
						.passwordParameter("pwd")
						.defaultSuccessUrl("/loginResult?status=success", false)
						.failureHandler(loginFailureHandler)
						.permitAll())
				.logout(logout -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/main")
						.invalidateHttpSession(true))
				.authenticationProvider(authenticationProvider());
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
	    return http
	            .getSharedObject(AuthenticationManagerBuilder.class)
	            .authenticationProvider(authenticationProvider())
	            .build();
	}
}
