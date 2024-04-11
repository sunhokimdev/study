package com.example.study.schedule.api.dto;

import com.example.study.schedule.domain.ScheduleState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScheduleSaveDto {
    @Getter
    public static class Request {
        private long seqLesson;
        private ScheduleState scheduleState;
    }
}
