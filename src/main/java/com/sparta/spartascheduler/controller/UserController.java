package com.sparta.spartascheduler.controller;

import com.sparta.spartascheduler.dto.LoginRequestDto;
import com.sparta.spartascheduler.dto.UserRequestDto;
import com.sparta.spartascheduler.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserRequestDto requestDto){
        return userService.createUser(requestDto);
    }


    @GetMapping("/bad")
    public String bad(){
        return "bad";
    }

    @GetMapping("/good")
    public String good(){
        return "good";
    }

    @GetMapping("/login-page")
    public String login_page(){
        return "login_page";
    }
}
