package com.example.study.lesson.domain.repository;

import com.example.study.lesson.domain.Lesson;

public interface LessonRepository {
    void save(Lesson lesson);
    Lesson findBySeqLesson(long seqLesson);
}
