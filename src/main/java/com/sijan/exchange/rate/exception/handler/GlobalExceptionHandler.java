package com.sijan.exchange.rate.exception.handler;

import com.sijan.exchange.rate.dto.ApiErrorResponseDto;
import com.sijan.exchange.rate.exception.NotFoundException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponseDto> handleExceptions(NotFoundException exception, WebRequest webRequest) {
        ApiErrorResponseDto response = new ApiErrorResponseDto();
        response.setTimestamp(LocalDateTime.now().toString());
        response.setMessage(exception.getMessage());
        ResponseEntity<ApiErrorResponseDto> entity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        return entity;
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ApiErrorResponseDto> handleExceptions(FeignException exception, WebRequest webRequest) {
        ApiErrorResponseDto response = new ApiErrorResponseDto();
        response.setTimestamp(LocalDateTime.now().toString());
        response.setMessage(exception.getMessage());
        ResponseEntity<ApiErrorResponseDto> entity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        return entity;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponseDto> handleExceptions(IllegalArgumentException exception, WebRequest webRequest) {
        ApiErrorResponseDto response = new ApiErrorResponseDto();
        response.setTimestamp(LocalDateTime.now().toString());
        response.setMessage(exception.getMessage());
        ResponseEntity<ApiErrorResponseDto> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        return entity;
    }
}
