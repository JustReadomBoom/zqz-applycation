package com.zqz.service;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 2:30 PM 2020/7/17
 */

@ComponentScan(basePackages = {"com.zqz.service"})
@EnableTransactionManagement
@NacosPropertySource(dataId = "zhouqizhi", groupId = "ZQZ_TEST", autoRefreshed = true)
public class ServiceApplication implements WebMvcConfigurer {

    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
