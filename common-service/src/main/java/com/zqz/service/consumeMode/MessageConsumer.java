package com.zqz.service.consumeMode;

import com.alibaba.fastjson.JSON;
import com.zqz.service.bean.Message;
import com.zqz.service.entity.User;
import com.zqz.service.utils.RandomUtil;
import com.zqz.service.utils.SeqUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:32 2021/12/2
 */
@RestController
@RequestMapping("/message")
public class MessageConsumer {
    @Autowired
    private MessageQueue messageQueue;


    @GetMapping("/add")
    public Boolean addMessage(@RequestParam String type){
        Message message = new Message();
        message.setType(type);
        User user = new User();
        user.setName(SeqUtil.createShortSeq());
        user.setAddress(SeqUtil.createLongSqp());
        user.setSex(RandomUtil.getAsciiRandom(2));
        user.setAge(Integer.valueOf(RandomUtil.createRandom(true, 2)));
        message.setContent(JSON.toJSONString(user));
        messageQueue.push(message);
        return Boolean.TRUE;
    }
}
