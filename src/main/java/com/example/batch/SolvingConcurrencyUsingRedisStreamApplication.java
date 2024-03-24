package com.example.batch;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.example.batch.*", annotationClass = Mapper.class)
public class SolvingConcurrencyUsingRedisStreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolvingConcurrencyUsingRedisStreamApplication.class, args);
    }
}
