package com.millie.financemanager.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime timestamp,
        Integer code,
        String status,
        List<String> errors
) {

    public ApiError(Integer code, List<String> errors) {
        this(LocalDateTime.now(), code, HttpStatus.valueOf(code).name(), errors);

    }
}
