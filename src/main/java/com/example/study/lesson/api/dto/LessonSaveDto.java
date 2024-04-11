package com.example.study.lesson.api.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LessonSaveDto {
    @Getter
    public static class Request {
        private int lessonAmount;
    }
}
