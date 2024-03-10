drop table if exists my_cache;

create table my_cache (
    id integer not null auto_increment,
    cache_miss_count integer not null,
    primary key (id)
) engine=InnoDB;
