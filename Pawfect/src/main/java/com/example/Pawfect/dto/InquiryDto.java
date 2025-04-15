package com.example.Pawfect.dto;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryDto {
    private int inquiryId;
    private String inquiryTitle;
    private String userId;
    private String inquiryContent;
    private String inquiryReply;
    private boolean inquiryStatus;
    private String adminId;
    private Timestamp inquiryRegdate;
    
    private String formattedDate;
}
