package com.example.study.schedule.application;

import com.example.study.StudyApplication;
import com.example.study.lesson.application.LessonService;
import com.example.study.lesson.domain.Lesson;
import com.example.study.schedule.domain.Schedule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(classes = StudyApplication.class,
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScheduleServiceTest {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private LessonService lessonService;

    @Test
    public void testReadUnCommitCount() throws InterruptedException {
        Lesson lesson = Lesson.builder()
                .lessonAmount(50)
                .build();

        lessonService.save(lesson);

        int numberOfThreads = 1_000;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    scheduleService.saveByIsolationReadUnCommitted(lesson.getSeqLesson());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        List<Schedule> scheduleList = scheduleService.findScheduleListByLesson(lesson.getSeqLesson());
        assertThat(scheduleList.size()).isNotEqualTo(50);
    }

    @ParameterizedTest
    @CsvSource({"50,1000,50", "1000,1000,1000", "50,1,1"})
    public void testDistributedLock(int lessonAmount, int numberOfThreads, int expectedResult) throws InterruptedException {
        Lesson lesson = Lesson.builder()
                .lessonAmount(lessonAmount)
                .build();

        lessonService.save(lesson);

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    scheduleService.saveByDistributedLock(lesson.getSeqLesson());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        List<Schedule> scheduleList = scheduleService.findScheduleListByLesson(lesson.getSeqLesson());
        assertThat(scheduleList.size()).isEqualTo(expectedResult);
    }
}
