package com.zqz.service.consumeMode;

import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;
import com.zqz.service.bean.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:26 2021/12/2
 */
@Component
@Slf4j
public class MessageQueue {
    private LinkedBlockingQueue<Message> messages = new LinkedBlockingQueue<>();


    public void push(Message message) {
        try {
            log.info("新增消息:{}", JSON.toJSONString(message));
            messages.put(message);
        } catch (Exception e) {
            log.error("push message error:{}", e.getMessage(), e);
        }
    }


    public Message pull() {
        try {
            if (!messages.isEmpty()) {
                log.info("开始消费消息...");
                return messages.poll();
            }
        } catch (Exception e) {
            log.error("pull message error:{}", e.getMessage(), e);
            return null;
        }
        return null;
    }

    public Integer count() {
        try {
            return messages.size();
        } catch (Exception e) {
            log.error("count message error:{}", e.getMessage(), e);
            return null;
        }
    }
}
