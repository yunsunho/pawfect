package com.example.Pawfect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Pawfect.dto.UserDto;
import com.example.Pawfect.mapper.AdminMapper;

@Service
public class AdminService {
	@Autowired
	private AdminMapper adminMapper;
	
	public List<UserDto> getAllUsers() {
		return adminMapper.getAllUsers();
	}
}
