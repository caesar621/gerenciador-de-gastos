package com.millie.financemanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ApiError> handleApiException(ApiException ex) {

        ApiError apiError = new ApiError(ex.getStatus().value(), List.of(ex.getMessage()));

        return ResponseEntity.status(ex.getStatus()).body(apiError);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError> handleNotValidException(MethodArgumentNotValidException ex) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), (ex.getFieldErrors().stream().map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage()).toList()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}
