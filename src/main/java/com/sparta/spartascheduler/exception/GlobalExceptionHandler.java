package com.sparta.spartascheduler.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Slf4j(topic = "Validation 관련")
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> exampleResponseValidation(MethodArgumentNotValidException e) {
        Map<String, String> error = new HashMap<>();
        e.getAllErrors().forEach(
                c -> {
                    error.put(((FieldError) c).getField(), c.getDefaultMessage());
                    log.error(c.getDefaultMessage());
                }
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Map<String, String>> handleBadRequestException(HttpClientErrorException e) {
        Map<String, String> error = new HashMap<>();

        log.error("HttpClientErrorException.BadRequest", e);
        error.put(e.getStatusCode().toString(), e.getMessage());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException e) {
        Map<String, String> error = new HashMap<>();

        log.error("ResponseStatusException", e);
        error.put(e.getStatusCode().toString(), e.getMessage());

        return ResponseEntity.badRequest().body(error);
    }
}
