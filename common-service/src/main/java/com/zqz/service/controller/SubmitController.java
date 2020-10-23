package com.zqz.service.controller;

import com.alibaba.fastjson.JSON;
import com.zqz.service.anno.NoRepeatSubmit;
import com.zqz.service.model.ApiResult;
import com.zqz.service.model.UserBean;
import com.zqz.service.utils.HttpUtil;
import com.zqz.service.utils.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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

    @PostMapping("test")
    @NoRepeatSubmit
    public ApiResult testSubmit(@RequestBody UserBean userBean) {
        try {
            // 模拟业务场景
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ApiResult result = new ApiResult(200, "成功", userBean.getUserId());
        log.info("RESULT=[{}]", JSON.toJSONString(result));
        return result;
    }

    @GetMapping("/test2")
    public String test2Submit(){
        String url="http://localhost:7777/submit/test";
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for(int i=0; i<10; i++){
            executorService.submit(() -> {
                try {
                    countDownLatch.await();
                    System.out.println("Thread:"+Thread.currentThread().getName()+", time:"+System.currentTimeMillis());

                    UserBean bean = new UserBean();
                    bean.setUserId(UUID.randomUUID().toString());
                    Map<String, String> header = new HashMap<>();
                    header.put("Content-Type", "application/json");
                    header.put("Authorization", "123456");  //用户唯一标识

                    String resp = HttpUtil.postJson(url, header, JSON.toJSONString(bean));

                    System.out.println("RESP:"+Thread.currentThread().getName() + "," + resp);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        countDownLatch.countDown();
        return "END";
    }
}
