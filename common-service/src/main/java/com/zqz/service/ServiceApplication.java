package com.zqz.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 2:30 PM 2020/7/17
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.zqz.service"})
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}
