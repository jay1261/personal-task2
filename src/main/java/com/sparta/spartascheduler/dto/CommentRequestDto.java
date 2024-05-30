package com.sparta.spartascheduler.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String contents;
    private String username;
    private Long scheduleId;
}
