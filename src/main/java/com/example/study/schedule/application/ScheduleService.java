package com.example.study.schedule.application;

import com.example.study.schedule.domain.Schedule;

import java.util.List;

public interface ScheduleService {
    void saveByIsolationReadUnCommitted(long seqLesson);
    void saveByDistributedLock(long seqLesson);
    List<Schedule> findScheduleListByLesson(long seqLesson);
}
