package com.sparta.spartascheduler.service;

import com.sparta.spartascheduler.dto.CommentRequestDto;
import com.sparta.spartascheduler.dto.CommentResponseDto;
import com.sparta.spartascheduler.entitiy.Comment;
import com.sparta.spartascheduler.entitiy.Schedule;
import com.sparta.spartascheduler.repository.CommentRepository;
import com.sparta.spartascheduler.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    public CommentResponseDto createComment(CommentRequestDto requestDto) {
        // 선택한 일정의 id를 입력받지 않은 경우
        if (!StringUtils.hasText(requestDto.getScheduleId().toString())){
            throw new IllegalArgumentException("일정 id가 존재하지 않습니다.");
        }
        // 댓글 내용이 비어 있는 경우
        if(!StringUtils.hasText(requestDto.getContents())){
            throw new IllegalArgumentException("댓글 내용이 존재하지 않습니다.");
        }

        // 일정이 DB에 저장되지 않은 경우.
        Schedule schedule = scheduleRepository.findById(requestDto.getScheduleId()).orElseThrow(
                () -> new IllegalArgumentException("없는 일정 id 입니다.")
        );

        Comment comment = new Comment(requestDto);
        comment.setSchedule(schedule);

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }
}
