package com.sparta.spartascheduler.jwt;

import com.sparta.spartascheduler.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "Jwt 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenValue = jwtUtil.getTokenFromRequest(request);

        if(StringUtils.hasText(tokenValue)){
            tokenValue = jwtUtil.substringToken(tokenValue); // barer 제거
            log.info(tokenValue);

            if(!jwtUtil.validateToken(tokenValue)){
                log.error("Token error");
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(tokenValue); // 토큰에서 정보 가져오기


            try{
                setAuthentication(info.getSubject());
            } catch (Exception e){
                log.error(e.getMessage());
                return;
            }

        }
        filterChain.doFilter(request, response);
    }

    // 인증 처리
    public void setAuthentication(String username) {
        // 시큐리티 컨텍스트 생성
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        // username으로 인증 객체 생성
        Authentication authentication = createAuthentication(username);
        // 컨텍스트에 인증객체를 set
        context.setAuthentication(authentication);

        // 시큐리티 컨텍스트 홀더에 컨텍스트를 set
        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        // 유저가 존재하는지 확인하며 유저객체를 가져옴
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // UsernamePasswordAuthenticationToken에 유저를 넣은 인증객체 생성
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
