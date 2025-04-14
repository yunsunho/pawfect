package com.example.Pawfect.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.Pawfect.dto.UserDto;

@Mapper
public interface AdminMapper {
	public List<UserDto> getAllUsers();
}
