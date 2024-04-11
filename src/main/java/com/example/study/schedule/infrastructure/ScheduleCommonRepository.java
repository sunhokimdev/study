package com.example.study.schedule.infrastructure;

import com.example.study.lesson.domain.Lesson;
import com.example.study.schedule.domain.Schedule;
import com.example.study.schedule.domain.ScheduleState;
import com.example.study.schedule.domain.repository.ScheduleRepository;
import com.example.study.schedule.infrastructure.db.ScheduleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScheduleCommonRepository implements ScheduleRepository {
    private final ScheduleJpaRepository scheduleJpaRepository;

    @Override
    public void save(Schedule schedule) {
        scheduleJpaRepository.save(schedule);
    }

    @Override
    public List<Schedule> findAllReservationScheduleListByLesson(Lesson lesson) {
        return scheduleJpaRepository.findAllByLessonAndScheduleState(lesson, ScheduleState.R);
    }
}
