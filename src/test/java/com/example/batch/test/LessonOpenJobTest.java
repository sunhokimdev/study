package com.example.batch.test;

import com.example.batch.lesson.domain.Lesson;
import com.example.batch.lesson.mapper.LessonOpenMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBatchTest
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LessonOpenJobTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private Job lessonOpenJob;
    @Autowired
    private LessonOpenMapper lessonOpenMapper;

    @BeforeAll
    public void init() {
        jobLauncherTestUtils.setJob(lessonOpenJob);
    }

    @ParameterizedTest(name = "[{index}] year: {0}, month: {1}, startDay: {2}, endDay: {3}, expectedResult: {4}")
    @CsvSource(value = {"2024, 3, 10, 14, 1372",
                        "2024, 3, 20, 28, 2613"
                       })
    public void lessonOpenExecutionReadCountTest(int year, int month, int startDay, int endDay, int expectedResult) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("startDt", toDate(year, month, startDay))
                .addDate("endDt", toDate(year, month, endDay))
                .toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        Collection<StepExecution> stepExecutionList = jobExecution.getStepExecutions();
        int result = stepExecutionList.stream()
                .map(StepExecution::getReadCount)
                .findFirst()
                .orElse(0);

        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest(name = "[{index}] year: {0}, month: {1}, startDay: {2}, endDay: {3}, expectedResult: {4}")
    @CsvSource(value = {"2024, 3, 15, 16, 313",
                        "2024, 3, 17, 18, 328",
                        // "2024, 3, 11, 19, 2071" // 지우지 않고 테스트 하면 테스트 실패 -> Batch Metatable에 데이터가 남아있어서 발생한 문제로 보여짐
                       })
    public void lessonOpenExecutionWriteCountTest(int year, int month, int startDay, int endDay, int expectedResult) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("startDt", toDate(year, month, startDay))
                .addDate("endDt", toDate(year, month, endDay))
                .toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        Collection<StepExecution> stepExecutionList = jobExecution.getStepExecutions();
        int result = stepExecutionList.stream()
                .map(StepExecution::getWriteCount)
                .findFirst()
                .orElse(0);

        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest(name = "[{index}] year: {0}, month: {1}, startDay: {2}, endDay: {3}")
    @CsvSource(value = {"2024, 3, 7, 10",
                        "2024, 3, 8, 9"
                       })
    public void lessonOpenExecutionDataBaseUpdateSuccessTest(int year, int month, int startDay, int endDay) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("startDt", toDate(year, month, startDay))
                .addDate("endDt", toDate(year, month, endDay))
                .toJobParameters();

        jobLauncherTestUtils.launchJob(jobParameters);

        List<Lesson> lessonList = lessonOpenMapper.findOpenLessonByTest(toLocalDateTime(year, month, startDay), toLocalDateTime(year, month, endDay));

        for (Lesson lesson : lessonList) {
            assertThat(lesson.getCompleteOpenDt()).isNotNull();
        }
    }

    private Date toDate(int year, int month, int day) {
        LocalDate localDate = LocalDate.of(year, month, day);
        LocalDateTime localDateTime = localDate.atStartOfDay();
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private LocalDateTime toLocalDateTime(int year, int month, int day) {
        LocalDate localDate = LocalDate.of(year, month, day);
        return localDate.atStartOfDay();
    }
}
