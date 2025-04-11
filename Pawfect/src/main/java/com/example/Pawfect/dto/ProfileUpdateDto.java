package com.example.Pawfect.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateDto {
    private String userId;       
    private String userNickname; 
    private String petName;      
    private Integer petType;     
}
