package com.example.study.lesson.infrastructure;

import com.example.study.lesson.domain.Lesson;
import com.example.study.lesson.domain.repository.LessonRepository;
import com.example.study.lesson.infrastructure.db.LessonJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LessonCommonRepository implements LessonRepository {
    private final LessonJpaRepository lessonJpaRepository;

    @Override
    public void save(Lesson lesson) {
        lessonJpaRepository.save(lesson);
    }

    @Override
    public Lesson findBySeqLesson(long seqLesson) {
        return lessonJpaRepository.findById(seqLesson)
                .orElse(null);
    }
}
