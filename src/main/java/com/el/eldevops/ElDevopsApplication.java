package com.el.eldevops;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableProcessApplication
@SpringBootApplication(scanBasePackages = "com.el.*")
@MapperScan("com.el.*")
@EnableScheduling
public class ElDevopsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElDevopsApplication.class, args);
    }

}
