package com.zqz.app.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:36 AM 2020/7/10
 */
@SpringBootApplication
@EnableEurekaClient
public class ProviderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderServiceApplication.class);
    }
}
