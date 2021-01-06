package com.zqz.service.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zqz.service.anno.NoRepeatSubmit;
import com.zqz.service.locallock.LocalLock;
import com.zqz.service.model.ApiResult;
import com.zqz.service.model.UserBean;
import com.zqz.service.utils.HttpUtil;
import com.zqz.service.utils.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:25 2020/10/23
 */
@RestController
@RequestMapping("/submit")
public class SubmitController {
    private static final Logger log = LoggerFactory.getLogger(SubmitController.class);
    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    //redis分布式锁放重复提交
    @PostMapping("test")
    @NoRepeatSubmit
    public ApiResult testSubmit(@RequestBody UserBean userBean) {
        try {
            // 模拟业务场景
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ApiResult(200, "成功", userBean.getUserId());
    }


    @GetMapping("/test2")
    public String test2Submit(){
        String url="http://localhost:7777/submit/test";
        for(int i=0; i<200; i++){
            executorService.submit(() -> {
                try {
                    System.out.println("Thread:"+Thread.currentThread().getName()+", time:"+System.currentTimeMillis());

                    UserBean bean = new UserBean();
                    bean.setUserId(UUID.randomUUID().toString());
                    Map<String, String> header = new HashMap<>();
                    header.put("Content-Type", "application/json");
                    header.put("Authorization", "123456");  //用户唯一标识

                    String resp = HttpUtil.postJson(url, header, JSON.toJSONString(bean));
                    log.info("线程:[{}]-测试响应结果:[{}]", Thread.currentThread().getName(), resp);
                    ApiResult apiResult = JSON.toJavaObject(JSON.parseObject(resp), ApiResult.class);
                    if("成功".equals(apiResult.getMessage())){
                        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>成功处理请求<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return "END";
    }


    //指定时间内防止重复提交
    @LocalLock(key = "arg[0]")
    @GetMapping("/test3")
    public String test3Submit(@RequestParam("token") String token){
        return "OK_" + token;
    }
}
