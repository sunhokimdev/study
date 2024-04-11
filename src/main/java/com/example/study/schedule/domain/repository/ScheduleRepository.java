package com.example.study.schedule.domain.repository;

import com.example.study.lesson.domain.Lesson;
import com.example.study.schedule.domain.Schedule;

import java.util.List;

public interface ScheduleRepository {
    void save(Schedule schedule);
    List<Schedule> findAllReservationScheduleListByLesson(Lesson lesson);
}
