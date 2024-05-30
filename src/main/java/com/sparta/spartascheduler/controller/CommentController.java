package com.sparta.spartascheduler.controller;

import com.sparta.spartascheduler.dto.CommentRequestDto;
import com.sparta.spartascheduler.dto.CommentResponseDto;
import com.sparta.spartascheduler.entitiy.Comment;
import com.sparta.spartascheduler.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    private CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto){
        return commentService.createComment(requestDto);
    }

    @PutMapping("/comment/{id}")
    private CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(id, requestDto);
    }

    @DeleteMapping("/comment/{id}")
    private ResponseEntity<String> deleteComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto){
        return commentService.deleteComment(id, requestDto);
    }
}
