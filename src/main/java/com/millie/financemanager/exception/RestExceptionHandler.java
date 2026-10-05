package com.millie.financemanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tools.jackson.databind.exc.InvalidFormatException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ApiError> handleNotReadableException(HttpMessageNotReadableException ex) {

        String message = "Malformed request body";

        if (ex.getCause() instanceof InvalidFormatException instanceOfInvalidFormatException) {

            String value = instanceOfInvalidFormatException.getValue().toString();

            String field = instanceOfInvalidFormatException.getPath().stream().map(
                    reference -> reference.getPropertyName()
            ).collect(Collectors.joining("."));

            Class<?> targetType = instanceOfInvalidFormatException.getTargetType();
            if (targetType.isEnum()) {
                String acceptedEnums = Arrays.stream(targetType.getEnumConstants()).map(Object::toString).collect(Collectors.joining(", "));

                message = "Invalid value " + value + " for field " + field + ". Accepted values: " + acceptedEnums;
            } else {
                message = "Invalid value " + value + " for field " + field;

            }

        }
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), List.of(message));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}
