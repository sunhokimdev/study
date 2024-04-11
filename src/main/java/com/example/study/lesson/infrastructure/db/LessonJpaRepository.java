package com.example.study.lesson.infrastructure.db;

import com.example.study.lesson.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonJpaRepository extends JpaRepository<Lesson, Long> {
}
