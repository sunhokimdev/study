package com.example.study.lesson.application;

import com.example.study.lesson.domain.Lesson;
import com.example.study.lesson.domain.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void save(Lesson lesson) {
        lessonRepository.save(lesson);
    }
}
