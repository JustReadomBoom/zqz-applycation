package com.zqz.service.consumeMode;

import com.alibaba.fastjson.JSON;
import com.zqz.service.bean.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:22 2021/12/3
 */
@Service
@Slf4j
public class ProcessMessageService {


    public void processMsg(Message message) {
        String type = message.getType();
        String content = message.getContent();
        String name = Optional.ofNullable(content).map(JSON::parseObject).map(j -> j.getString("name")).orElse(null);
        Integer age = Optional.ofNullable(content).map(JSON::parseObject).map(j -> j.getInteger("age")).orElse(0);
        String sex = Optional.ofNullable(content).map(JSON::parseObject).map(j -> j.getString("sex")).orElse(null);
        String address = Optional.ofNullable(content).map(JSON::parseObject).map(j -> j.getString("address")).orElse(null);
        log.info("====> 执行任务，type:{}, name:{}, age:{}, sex:{}, address:{}", type, name, age, sex, address);
    }
}
