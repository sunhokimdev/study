drop table IF EXISTS LESSON;

create table LESSON (
    id integer not null auto_increment,
    lesson_name varchar(100) not null,
    lesson_time integer not null,
    start_dt datetime not null,
    open_dt datetime,
    complete_open_dt datetime,
    primary key (id)
);

