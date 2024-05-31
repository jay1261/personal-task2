package com.sparta.spartascheduler.service;

import com.sparta.spartascheduler.dto.UserRequestDto;
import com.sparta.spartascheduler.entitiy.User;
import com.sparta.spartascheduler.entitiy.UserRoleEnum;
import com.sparta.spartascheduler.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public ResponseEntity<String> createUser(UserRequestDto requestDto) {

        // 유저네임 중복 확인
        Optional<User> byUsername = userRepository.findByUsername(requestDto.getUsername());
        if(byUsername.isPresent()){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "중복된 username 입니다.");
        }

        // 사용자 권한 확인
        UserRoleEnum userRoleEnum = UserRoleEnum.USER;
        if (requestDto.isAdmin()){
            if(!ADMIN_TOKEN.equals(requestDto.getAdminToken())){
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "관리자 암호가 틀려 등록이 불가능합니다.");
            }
            userRoleEnum = UserRoleEnum.ADMIN;
        }

        User user = new User(requestDto, userRoleEnum);
        userRepository.save(user);

        return new ResponseEntity<String>("회원가입에 성공했습니다.", HttpStatus.CREATED);
    }
}
