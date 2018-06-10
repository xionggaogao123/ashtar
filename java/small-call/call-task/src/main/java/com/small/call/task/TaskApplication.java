package com.small.call.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author keven
 * @date 2018-06-10 下午3:44
 * @Description
 */
@SpringBootApplication
public class TaskApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TaskApplication.class);
        application.run(args);
    }
}
