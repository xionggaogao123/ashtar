package com.ashstr.spider;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author keven
 * @date 2018-06-09 下午6:14
 * @Description
 */
@EnableWebMvc
@EnableAutoConfiguration
@EnableScheduling
@Configuration
@ComponentScan({"com.ashstr.spider"})
public class SpiderConfiguration {



}
