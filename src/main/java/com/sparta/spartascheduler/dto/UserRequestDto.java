package com.sparta.spartascheduler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserRequestDto {
    @NotBlank
    private String nickname;

    @NotBlank(message = "아이디를 입력해 주세요.")
    @Pattern(regexp = "([a-z0-9]).{4,10}", message = "아이디는 4~10자 알파벳 소문자와, 숫자를 사용하세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Pattern(regexp = "([a-zA-Z0-9]).{8,15}", message = "비밀번호는 8~15자 알파벳 대소문자와, 숫자를 사용하세요.")
    private String password;

    @NotNull(message = "권한을 지정해야 합니다.")
    private boolean admin = false;

    private String adminToken = "";
}
