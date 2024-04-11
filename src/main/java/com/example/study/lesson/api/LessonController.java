package com.example.study.lesson.api;

import com.example.study.lesson.api.dto.LessonSaveDto;
import com.example.study.lesson.application.LessonService;
import com.example.study.lesson.domain.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lesson")
public class LessonController {
    private final LessonService lessonService;

    @PostMapping
    public void save(@RequestBody LessonSaveDto.Request request) {
        Lesson lesson = Lesson.builder()
                .lessonAmount(request.getLessonAmount())
                .build();

        lessonService.save(lesson);
    }
}
