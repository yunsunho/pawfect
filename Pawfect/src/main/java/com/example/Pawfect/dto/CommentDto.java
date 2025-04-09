package com.example.Pawfect.dto;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private String userId;
    private int postId;
    private String comContent;
    private Timestamp comRegdate;
    private int com_ref;
    private int com_re_step;
    private int com_re_level;
    private boolean comEditStatus;
    private boolean comDeleteStatus;
}