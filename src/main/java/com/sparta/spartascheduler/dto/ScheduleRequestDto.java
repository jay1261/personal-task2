package com.sparta.spartascheduler.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private Long id;
    private String title;
    private String contents;
    private String username;
    private String password;
}
