package com.sparta.spartascheduler.controller;

import com.sparta.spartascheduler.dto.UserRequestDto;
import com.sparta.spartascheduler.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserRequestDto requestDto){
        return userService.createUser(requestDto);
    }


}
