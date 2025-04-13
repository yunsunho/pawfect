package com.example.Pawfect.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String uploadPath = System.getProperty("user.dir") + "/upload/profile/";
		registry.addResourceHandler("/profile-img/**").addResourceLocations("file:///" + uploadPath);
	}
}