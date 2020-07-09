package com.zqz.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 5:46 PM 2020/7/7
 */
@Service
@Slf4j
public class RetryTestService {
    private static final int totalNum = 100000;


    @Retryable(value = Exception.class, backoff = @Backoff(delay = 2000, multiplier = 1.5))
    public int minGoodsnum(int num) throws Exception{
        log.info("minGoodsnum开始" + LocalTime.now());
        if (num <= 0) {
            throw new Exception("数量不对");
        }
        log.info("minGoodsnum执行结束");
        return totalNum - num;
    }

    @Recover
    public int recover(Exception e){
        log.info("请求失败，开始重试,e=[{}]", e.getMessage());
        return 200;
    }

}
