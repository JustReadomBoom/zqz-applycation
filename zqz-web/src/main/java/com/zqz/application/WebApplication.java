package com.zqz.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 5:11 PM 2020/7/7
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.zqz.application")
@EnableRetry
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
