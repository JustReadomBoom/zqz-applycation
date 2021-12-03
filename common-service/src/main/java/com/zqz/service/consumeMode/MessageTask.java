package com.zqz.service.consumeMode;

import com.alibaba.fastjson.JSON;
import com.zqz.service.bean.Message;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:37 2021/12/2
 */
@Slf4j
public class MessageTask implements Runnable {

    private MessageQueue messageQueue;
    private ProcessMessageService processMessageService;

    public MessageTask(MessageQueue messageQueue, ProcessMessageService processMessageService){
        this.messageQueue = messageQueue;
        this.processMessageService = processMessageService;
    }

    @Override
    public void run() {
        Message message = messageQueue.pull();
        if(null != message){
            processMessageService.processMsg(message);
            log.info("剩余消息数量:{}", JSON.toJSONString(messageQueue.count()));
        }
    }
}
