package com.example.batch.lesson.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    private int id;
    private String lessonName;
    private int lessonTime;
    private LocalDateTime startDt;
    private LocalDateTime openDt;
    @Setter
    private LocalDateTime completeOpenDt;
}
