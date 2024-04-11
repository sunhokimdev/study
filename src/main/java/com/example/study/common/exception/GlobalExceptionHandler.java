package com.example.study.common.exception;

import com.example.study.common.http.CommonResponse;
import com.example.study.schedule.application.exception.ScheduleException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ScheduleException.class)
    private ResponseEntity<CommonResponse> handleScheduleException(ScheduleException exception) {
        log.error(">>>>>>>>>> handleScheduleException code ::: {} message::: {}", exception.getErrorCode(), exception.getMessage(), exception);
        return new ResponseEntity<>(
                CommonResponse.builder()
                        .errorCode(exception.getErrorCode())
                        .message(exception.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
}
