package com.example.study.schedule.infrastructure.db;

import com.example.study.lesson.domain.Lesson;
import com.example.study.schedule.domain.Schedule;
import com.example.study.schedule.domain.ScheduleState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleJpaRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByLessonAndScheduleState(Lesson lesson, ScheduleState scheduleState);
}
