package com.sparta.spartascheduler.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.spartascheduler.dto.LoginRequestDto;
import com.sparta.spartascheduler.entitiy.UserRoleEnum;
import com.sparta.spartascheduler.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

@Slf4j(topic = "로그인 및 JWT 생성")

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        // 이 필터가 동작할 url 지정
        setFilterProcessesUrl("/users/login");
    }

    @Override
    // 인증 시도
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try{
            // json 형태의 데이터를 object로 바꾸기
            LoginRequestDto loginRequestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            // 인증 처리 역할 수행 , 이 클래스의 객체를 만들 때 AuthenticationManager를 넣어줘야함.
            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword(),
                        null
                )
            );

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    // 로그인 성공했을 시 실행
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 jwt 생성");
        // Authentication 인증 객체에서 principal에 있는 유저 가져오기
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        // 토큰 만들고 쿠키에 토큰 저장
        String token = jwtUtil.createToken(username, role);
        jwtUtil.addJwtToCookie(token, response);
        response.setStatus(201);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.error("로그인 실패");
        response.setStatus(401);
    }

}