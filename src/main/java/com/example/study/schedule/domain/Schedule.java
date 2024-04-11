package com.example.study.schedule.domain;

import com.example.study.lesson.domain.Lesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seqSchedule;
    @Column(columnDefinition = "enum ('R', 'C')")
    @Enumerated(EnumType.STRING)
    private ScheduleState scheduleState;
    @ManyToOne
    @JoinColumn(name = "seq_lesson")
    private Lesson lesson;
}
