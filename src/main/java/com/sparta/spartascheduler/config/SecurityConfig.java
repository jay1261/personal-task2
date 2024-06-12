package com.sparta.spartascheduler.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable());

        http.authorizeHttpRequests((authorizeHttpRequests)->
            authorizeHttpRequests
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resource 접근 허용
                    .requestMatchers("/api/user/**").permitAll() // user 이하 모든 api 접근 허용
                    .requestMatchers(HttpMethod.POST,"/user/login").permitAll()
                    .anyRequest().authenticated() // 나머지 api인증 처리
        );

        http.formLogin((formLogin) ->
            formLogin
                    .loginPage("/api/login-page")
                    .loginProcessingUrl("/user/login")
                    .defaultSuccessUrl("/api/good")
                    .failureUrl("/api/bad")
                    .permitAll()
        );

        return http.build();
    }
}
