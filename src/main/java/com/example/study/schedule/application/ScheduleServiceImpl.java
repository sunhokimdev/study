package com.example.study.schedule.application;

import com.example.study.common.exception.ErrorCode;
import com.example.study.lesson.domain.Lesson;
import com.example.study.lesson.domain.repository.LessonRepository;
import com.example.study.schedule.application.exception.ScheduleException;
import com.example.study.schedule.domain.Schedule;
import com.example.study.schedule.domain.ScheduleState;
import com.example.study.schedule.domain.repository.ScheduleRepository;
import com.example.study.common.task.DistributedLock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final LessonRepository lessonRepository;

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void saveByIsolationReadUnCommitted(long seqLesson) {
        validateLessonAmount(seqLesson);
        scheduleRepository.save(createReservationSchedule(seqLesson));
    }

    @Override
    @Transactional
    @DistributedLock(lockPrefix = "ScheduleLock:", key = "#seqLesson", leaseTime = 1L, waitTime = 60L)
    public void saveByDistributedLock(long seqLesson) {
        validateLessonAmount(seqLesson);
        scheduleRepository.save(createReservationSchedule(seqLesson));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Schedule> findScheduleListByLesson(long seqLesson) {
        Lesson lesson = Lesson.builder()
                .seqLesson(seqLesson)
                .build();

        return scheduleRepository.findAllReservationScheduleListByLesson(lesson);
    }

    private void validateLessonAmount(long seqLesson) {
        Lesson lesson = lessonRepository.findBySeqLesson(seqLesson);
        List<Schedule> scheduleList = scheduleRepository.findAllReservationScheduleListByLesson(lesson);

        if (scheduleList.size() >= lesson.getLessonAmount()) {
            throw new ScheduleException(ErrorCode.ScheduleErrorCode.S00001);
        }
    }

    private Schedule createReservationSchedule(long seqLesson) {
        return Schedule.builder()
                .scheduleState(ScheduleState.R)
                .lesson(Lesson.builder()
                        .seqLesson(seqLesson)
                        .build())
                .build();
    }
}
