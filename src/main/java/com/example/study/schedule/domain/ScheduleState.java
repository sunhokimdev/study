package com.example.study.schedule.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScheduleState {
    R("스케줄 예약"),
    C("스케줄 취소");

    private final String comment;
}
