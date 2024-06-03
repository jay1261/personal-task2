package com.sparta.spartascheduler.service;

import com.sparta.spartascheduler.dto.ScheduleRequestDto;
import com.sparta.spartascheduler.dto.ScheduleResponseDto;
import com.sparta.spartascheduler.entitiy.Schedule;
import com.sparta.spartascheduler.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule(scheduleRequestDto);

        Schedule saved = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(saved);
    }

    public ScheduleResponseDto getSchedule(Long id) {
        Optional<Schedule> result = scheduleRepository.findById(id);
        Schedule schedule = result.orElseThrow(() -> new IllegalArgumentException("선택한 일정은 존재하지 않습니다."));

        return new ScheduleResponseDto(schedule);
    }

    public List<ScheduleResponseDto> getSchedules() {
        return scheduleRepository.findAllByOrderByCreatedAtDesc().stream().map(ScheduleResponseDto::new).toList();
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = findScheduleByIdAndPassword(id, scheduleRequestDto.getPassword());

        schedule.update(scheduleRequestDto);

        return new ScheduleResponseDto(schedule);
    }


    public Long deleteSchedule(Long id, String password) {
        Schedule scedule = findScheduleByIdAndPassword(id, password);

        scheduleRepository.delete(scedule);

        return id;
    }

    private Schedule findScheduleByIdAndPassword(Long id, String password){
        return scheduleRepository.findByIdIsAndPassword(id, password).orElseThrow(
                ()-> new IllegalArgumentException("선택한 일정이 없거나, 비밀번호가 맞지 않습니다.")
        );
    }

    public Schedule findById(Long id){
        return scheduleRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "선택한 일정은 존재하지 않습니다.")
        );
    }
}
