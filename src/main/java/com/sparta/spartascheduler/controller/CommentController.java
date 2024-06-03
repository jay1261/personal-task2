package com.sparta.spartascheduler.controller;

import com.sparta.spartascheduler.dto.CommentRequestDto;
import com.sparta.spartascheduler.dto.CommentResponseDto;
import com.sparta.spartascheduler.entitiy.Comment;
import com.sparta.spartascheduler.entitiy.User;
import com.sparta.spartascheduler.service.CommentService;
import jakarta.persistence.Version;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public CommentResponseDto createComment(@RequestBody @Valid CommentRequestDto requestDto, HttpServletRequest request){
        User loginUser = (User) request.getAttribute("user");
        return commentService.createComment(requestDto, loginUser);
    }

    @PutMapping("/comment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody @Valid CommentRequestDto requestDto, HttpServletRequest request) {
        User loginUser = (User) request.getAttribute("user");
        return commentService.updateComment(id, requestDto, loginUser);
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id, @RequestBody @Valid CommentRequestDto requestDto, HttpServletRequest request){
        User loginUser = (User) request.getAttribute("user");
        return commentService.deleteComment(id, requestDto, loginUser);
    }

    @GetMapping("/comments")
    public List<CommentResponseDto> getAllComments(){
        return commentService.getAllComments();
    }

}
