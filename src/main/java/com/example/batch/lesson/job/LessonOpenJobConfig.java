package com.example.batch.lesson.job;

import com.example.batch.lesson.domain.Lesson;
import com.example.batch.lesson.mapper.LessonOpenMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class LessonOpenJobConfig {
    private final SqlSessionFactory sqlSessionFactory;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final LessonOpenMapper lessonOpenMapper;

    private static final int CHUNK_SIZE = 100;
    private static final String QUERY_ID = "com.example.batch.lesson.mapper.LessonOpenMapper.findOpenLesson";
    private static final String JOB_NAME = "lessonOpenJob";
    private static final String STEP_NAME = "lessonOpenStep";

    @Bean
    public Job lessonOpenJob(Step lessonOpenStep) {
        return new JobBuilder(JOB_NAME)
                .start(lessonOpenStep)
                .repository(jobRepository)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step lessonOpenStep() {
        return new StepBuilder(STEP_NAME)
                .<Lesson, Lesson> chunk(CHUNK_SIZE)
                .reader(itemReader(null, null))
                .writer(itemWriter())
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .startLimit(1)
                .build();
    }

    @Bean
    @StepScope
    public MyBatisPagingItemReader<Lesson> itemReader(
            @Value("#{jobParameters[startDt]}") Date startDt,
            @Value("#{jobParameters[endDt]}") Date endDt) {

        return new MyBatisPagingItemReaderBuilder<Lesson>()
                .queryId(QUERY_ID)
                .pageSize(CHUNK_SIZE)
                .sqlSessionFactory(this.sqlSessionFactory)
                .parameterValues(getParameterValues(startDt, endDt))
                .build();

        // page 사이즈를 항상 0으로 설정하도록 오버라이딩한다.
        /*
        MyBatisPagingItemReader<Lesson> myBatisPagingItemReader = new MyBatisPagingItemReader<Lesson>() {
            @Override
            public int getPage() {
                return 0;
            }
        };
        myBatisPagingItemReader.setPageSize(CHUNK_SIZE);
        myBatisPagingItemReader.setSqlSessionFactory(sqlSessionFactory);
        myBatisPagingItemReader.setQueryId(QUERY_ID);
        myBatisPagingItemReader.setParameterValues(getParameterValues(startDt, endDt));
        return myBatisPagingItemReader;
        */
    }

    @Bean
    public ItemWriter<Lesson> itemWriter() {
        return items -> {
            // 아래 코드는 DB I/O의 부하를 발생시키는 코드
            /*
            LocalDateTime now = LocalDateTime.now();
            for (Lesson item : items) {
                item.setCompleteOpenDt(now);
                // lessonOpenMapper.updateLesson(item);
            }
            */
            lessonOpenMapper.updateBulkLesson(new ArrayList<>(items));
        };
    }

    private Map<String, Object> getParameterValues(Date startDt, Date endDt) {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("startDt", startDt);
        parameterValues.put("endDt", endDt);
        return parameterValues;
    }
}
