package com.sparta.spartascheduler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    @NotBlank(message = "내용이 비어있습니다.")
    private String contents;
    @NotBlank(message = "유저id를 입력해주세요")
    private String username;
    @NotNull(message = "일정의 id를 입력해주세요")
    private Long scheduleId;
}
