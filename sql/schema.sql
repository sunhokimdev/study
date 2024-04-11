drop table if exists lesson;
drop table if exists schedule;

create table lesson (
    seq_lesson bigint not null auto_increment,
    lesson_amount integer,
    primary key (seq_lesson)
) engine=InnoDB;

create table schedule (
    seq_schedule bigint not null auto_increment,
    seq_lesson bigint not null,
    schedule_state enum ('C', 'R'),
    primary key (seq_schedule),
    FOREIGN KEY (seq_lesson) REFERENCES lesson(seq_lesson)
) engine=InnoDB;