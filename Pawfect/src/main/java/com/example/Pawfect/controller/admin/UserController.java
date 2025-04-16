package com.example.Pawfect.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Pawfect.dto.UserDto;
import com.example.Pawfect.mapper.AdminMapper;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Resource
    private AdminMapper adminMapper;


    @GetMapping
//    public List<UserDto> getAllUsers() {
//        return adminMapper.getAllUsers();
//    }
    
    @DeleteMapping
    public void deleteUser(@PathVariable String userId) {
    	//return adminMapper.deleteUser(userId);
    }
}

