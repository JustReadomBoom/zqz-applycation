package com.zqz.service.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 14:27 2020/11/30
 */
@RequestMapping("/test")
@RestController
@Slf4j
public class TestController {

    @NacosValue(value = "${configName:ZQZ}", autoRefreshed = true)
    private String configName;


    @NacosValue(value = "${configTest:ZQZ}", autoRefreshed = true)
    private String configTest;

    @GetMapping("/config")
    public String testConfig(){
        return configTest;
    }
}
