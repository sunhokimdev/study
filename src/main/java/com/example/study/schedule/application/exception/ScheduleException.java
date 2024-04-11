package com.example.study.schedule.application.exception;

import com.example.study.common.exception.ErrorCode;

public class ScheduleException extends RuntimeException {
    private final ErrorCode.ScheduleErrorCode errorCode;

    public ScheduleException(Throwable cause, ErrorCode.ScheduleErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ScheduleException(ErrorCode.ScheduleErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode.ScheduleErrorCode getErrorCode() {
        return errorCode;
    }
}
