package com.ashstr.spider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author keven
 * @date 2018-06-09 下午6:13
 * @Description
 */
@SpringBootApplication
public class SpiderApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpiderApplication.class);
        application.run(args);
    }
}
