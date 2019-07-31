package com.xxh.start1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.xxh.start1.mapper")
public class Start1Application {

    public static void main(String[] args) {
        SpringApplication.run(Start1Application.class, args);
    }

}
