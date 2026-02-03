package com.pragma.ms_personas.infrastructure.exception;

import com.pragma.ms_personas.domain.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleTechnologyAlreadyExists(
            BadRequestException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .errors(Arrays.asList(ex.getMessage().split("\\|")))
                .timestamp(LocalDateTime.now()).build();

        return buildResponse(HttpStatus.BAD_REQUEST, error);
    }

    private Mono<ResponseEntity<ErrorResponse>> buildResponse(HttpStatus status, ErrorResponse error) {
        return Mono.just(ResponseEntity
                .status(status)
                .body(error)
        );
    }
}
