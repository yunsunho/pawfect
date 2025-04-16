package com.example.Pawfect.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 프로필 이미지 경로
		String uploadPath = System.getProperty("user.dir") + "/upload/profile/";
		registry.addResourceHandler("/profile-img/**").addResourceLocations("file:///" + uploadPath);

		// 리뷰 이미지 경로
		String reviewPath = System.getProperty("user.dir") + "/upload/review/";
		registry.addResourceHandler("/upload/review/**").addResourceLocations("file:///" + reviewPath);
		
		registry.addResourceHandler("/css/**")
        .addResourceLocations("classpath:/static/css/");
	}
}