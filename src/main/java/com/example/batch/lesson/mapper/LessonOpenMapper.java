package com.example.batch.lesson.mapper;

import com.example.batch.lesson.domain.Lesson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LessonOpenMapper {
    List<Lesson> findOpenLesson();
    void updateLesson(Lesson lesson);
    List<Lesson> findOpenLessonByTest(@Param("startDt") LocalDateTime startDt,
                                      @Param("endDt") LocalDateTime endDt);
    void updateBulkLesson(@Param("lesson") List<Lesson> items);
}
