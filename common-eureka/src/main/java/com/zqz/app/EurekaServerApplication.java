package com.zqz.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:24 AM 2020/7/10
 */
@SpringBootApplication
@EnableEurekaServer    //标识是eureka服务端
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class);
    }
}
