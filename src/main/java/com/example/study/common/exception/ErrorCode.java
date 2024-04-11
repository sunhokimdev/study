package com.example.study.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ErrorCode {
    @Getter
    @RequiredArgsConstructor
    public enum ScheduleErrorCode implements Code {
        S00001("그룹수업 정원이 찼습니다.");

        private final String message;
    }
}
