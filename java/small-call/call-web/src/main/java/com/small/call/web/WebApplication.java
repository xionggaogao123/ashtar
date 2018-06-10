package com.small.call.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author keven
 * @date 2018-06-10 下午3:43
 * @Description
 */
@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(WebApplication.class);
        application.run(args);
    }
}
