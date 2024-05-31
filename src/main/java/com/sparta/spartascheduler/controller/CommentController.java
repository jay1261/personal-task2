package com.sparta.spartascheduler.controller;

import com.sparta.spartascheduler.dto.CommentRequestDto;
import com.sparta.spartascheduler.dto.CommentResponseDto;
import com.sparta.spartascheduler.entitiy.Comment;
import com.sparta.spartascheduler.service.CommentService;
import jakarta.persistence.Version;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    private CommentResponseDto createComment(@RequestBody @Valid CommentRequestDto requestDto, HttpServletRequest request){
        return commentService.createComment(requestDto, request);
    }

    @PutMapping("/comment/{id}")
    private CommentResponseDto updateComment(@PathVariable Long id, @RequestBody @Valid CommentRequestDto requestDto, HttpServletRequest request) {
        return commentService.updateComment(id, requestDto, request);
    }

    @DeleteMapping("/comment/{id}")
    private ResponseEntity<String> deleteComment(@PathVariable Long id, @RequestBody @Valid CommentRequestDto requestDto, HttpServletRequest request){
        return commentService.deleteComment(id, requestDto, request);
    }
}
