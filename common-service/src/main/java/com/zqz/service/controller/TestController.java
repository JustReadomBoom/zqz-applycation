package com.zqz.service.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.zqz.service.entity.OrderRecord;
import com.zqz.service.mapper.OrderRecordService;
import com.zqz.service.model.ApiResult;
import com.zqz.service.model.UserBean;
import com.zqz.service.redis.RedisLock;
import com.zqz.service.utils.HttpUtil;
import com.zqz.service.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 14:27 2020/11/30
 */
@RequestMapping("/test")
@RestController
@Slf4j
public class TestController {

    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    @Autowired
    private OrderRecordService orderRecordService;

    private Map<String, Object> lock = new Hashtable<>();
    @Autowired
    private RedisLock redisLock;

    @NacosValue(value = "${configName:ZQZ}", autoRefreshed = true)
    private String configName;


    @NacosValue(value = "${configTest:ZQZ}", autoRefreshed = true)
    private String configTest;

    @GetMapping("/config")
    public String testConfig() {
        return configTest;
    }

    /**
     * @Author: zqz
     * @Description: 利用redis分布式锁防止请求重复
     * @Param: [userBean]
     * @Return: com.zqz.service.model.ApiResult
     * @Date: 14:40 2021/2/3
     */
    @PostMapping("/redis/repeat")
    public ApiResult reqHttpRedisLockRepeat(@RequestBody UserBean userBean) {
        String userName = userBean.getUserName();
        String userId = userBean.getUserId();
        String requestNo = userBean.getRequestNo();
        log.info("userName:{}, userId:{}, requestNo:{}", userName, userId, requestNo);
        try {
            boolean success = redisLock.reqTryLock(requestNo, userId, 2);
            if (success) {
                List<OrderRecord> records = orderRecordService.selectByOrderId(requestNo);
                if (records.isEmpty()) {
                    List<OrderRecord> list = new ArrayList<>();
                    OrderRecord record = new OrderRecord();
                    record.setName(userName);
                    record.setAddress("111111");
                    record.setAmount(BigDecimal.TEN);
                    record.setOrderId(requestNo);
                    list.add(record);
                    orderRecordService.insertBatch(list);
                } else {
                    log.info("requestNo:{} 订单已存在", requestNo);
                }
                return new ApiResult(200, "成功", userBean.getRequestNo());
            } else {
                return new ApiResult(200, "订单重复", userBean.getRequestNo());
            }
        } catch (Exception e) {
            log.error("*****tryLock异常:[{}]", e.getMessage(), e);
            return new ApiResult(200, "tryLock异常", userBean.getRequestNo());
        } finally {
            redisLock.reqReleaseLock(requestNo, userId);
        }
    }

    /**
     * @Author: zqz
     * @Description: 单机部署情况下利用Hashtable加锁防止请求重复
     * @Param: [userBean]
     * @Return: com.zqz.service.model.ApiResult
     * @Date: 14:41 2021/2/3
     */
    @PostMapping("/hash/repeat")
    public ApiResult reqHttpSingleHashLockRepeat(@RequestBody UserBean userBean) {
        String userName = userBean.getUserName();
        String userId = userBean.getUserId();
        String requestNo = userBean.getRequestNo();
        log.info("userName:{}, userId:{}, requestNo:{}", userName, userId, requestNo);
        Object lockVal = lock.get(requestNo);
        if (null == lockVal) {
            log.info("add lock, requestNo:{}", requestNo);
            lockVal = new Object();
            lock.put(requestNo, lockVal);
        }
        synchronized (lock) {
            try {
                List<OrderRecord> records = orderRecordService.selectByOrderId(requestNo);
                Thread.sleep(1000);
                if (records.isEmpty()) {
                    List<OrderRecord> list = new ArrayList<>();
                    OrderRecord record = new OrderRecord();
                    record.setName(userName);
                    record.setAddress("111111");
                    record.setAmount(BigDecimal.TEN);
                    record.setOrderId(requestNo);
                    list.add(record);
                    orderRecordService.insertBatch(list);
                } else {
                    log.info("requestNo:{} 订单已存在", requestNo);
                }
            } catch (Exception e) {
                log.error("*****error:{}", e.getMessage(), e);
                return new ApiResult(200, "异常", userBean.getRequestNo());
            }
        }
        lock.remove(requestNo);
        log.info("remove lock, requestNo:{}", requestNo);
        return new ApiResult(200, "成功", userBean.getRequestNo());
    }


    /**
     * @Author: zqz
     * @Description: 模拟并发请求
     * @Param: []
     * @Return: java.lang.String
     * @Date: 14:41 2021/2/3
     */
    @GetMapping("/send/repeat")
    public String test2Submit() {
        String url = "http://localhost:7777/test/repeat";
        for (int i = 0; i < 20; i++) {
            Integer cI;
            cI = i;
            executorService.submit(() -> {
                try {
                    System.out.println("Thread:" + Thread.currentThread().getName() + ", time:" + System.currentTimeMillis());

                    UserBean bean = new UserBean();
                    bean.setUserId(UUID.randomUUID().toString());
                    if (cI == 8 || cI == 9) {   //假设第8，9个请求为重复请求，单号重复
                        bean.setRequestNo("test000000001");
                    } else {
                        bean.setRequestNo(RandomUtil.createRandom(true, 12));
                    }
                    bean.setUserName("ZQZ" + cI);
                    Map<String, String> header = new HashMap<>();

                    String resp = HttpUtil.postJson(url, header, JSON.toJSONString(bean));
                    log.info("线程:[{}]-测试响应结果:[{}]", Thread.currentThread().getName(), resp);
                    ApiResult apiResult = JSON.toJavaObject(JSON.parseObject(resp), ApiResult.class);
                    if ("成功".equals(apiResult.getMessage())) {
                        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>成功处理请求<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return "END";
    }
}
