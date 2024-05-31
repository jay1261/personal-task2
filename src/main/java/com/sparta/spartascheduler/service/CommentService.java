package com.sparta.spartascheduler.service;

import com.sparta.spartascheduler.dto.CommentRequestDto;
import com.sparta.spartascheduler.dto.CommentResponseDto;
import com.sparta.spartascheduler.entitiy.Comment;
import com.sparta.spartascheduler.entitiy.Schedule;
import com.sparta.spartascheduler.entitiy.User;
import com.sparta.spartascheduler.repository.CommentRepository;
import com.sparta.spartascheduler.repository.ScheduleRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    public CommentResponseDto createComment(CommentRequestDto requestDto, HttpServletRequest request) {
        // 일정이 DB에 저장되지 않은 경우.
        Schedule schedule = scheduleRepository.findById(requestDto.getScheduleId()).orElseThrow(
                () -> new IllegalArgumentException("없는 일정 id 입니다.")
        );

        User loginUser = (User) request.getAttribute("user");
        Comment comment = new Comment(requestDto, loginUser.getUsername());
        comment.setSchedule(schedule);

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, HttpServletRequest request) {

        // 일정이나 댓글이 DB에 저장되지 않은 경우
        Schedule schedule = scheduleRepository.findById(requestDto.getScheduleId()).orElseThrow(
                () -> new IllegalArgumentException("선택한 일정은 존재하지 않습니다.")
        );

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.")
        );

        // 선택한 댓글의 사용자가 현재 사용자와 일치하지 않은 경우
        User loginUser = (User) request.getAttribute("user");
        if(!comment.getUsername().equals(loginUser.getUsername())){
            throw new IllegalArgumentException("댓글의 작성자가 아닙니다. 수정할 수 없습니다.");
        }

        comment.update(requestDto);

        return new CommentResponseDto(comment);
    }

    public ResponseEntity<String> deleteComment(Long id, CommentRequestDto requestDto, HttpServletRequest request) {
        // 일정이나 댓글이 DB에 저장되지 않은 경우
        Schedule schedule = scheduleRepository.findById(requestDto.getScheduleId()).orElseThrow(
                () -> new IllegalArgumentException("선택한 일정은 존재하지 않습니다.")
        );

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.")
        );

        // 선택한 댓글의 사용자가 현재 사용자와 일치하지 않은 경우
        User loginUser = (User) request.getAttribute("user");
        if(!comment.getUsername().equals(loginUser.getUsername())){
            throw new IllegalArgumentException("댓글의 작성자가 아닙니다. 수정할 수 없습니다.");
        }

        commentRepository.delete(comment);

        return new ResponseEntity<>("삭제가 완료되었습니다.", HttpStatus.OK);
    }

    public List<CommentResponseDto> getAllComments() {
        List<Comment> all = commentRepository.findAll();

        return all.stream().map(CommentResponseDto::new).toList();
    }
}
