//package com.zqz.service.consumeMode;
//
//import com.alibaba.fastjson.JSON;
//import com.zqz.service.bean.Message;
//import com.zqz.service.entity.User;
//import com.zqz.service.mapper.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledThreadPoolExecutor;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
///**
// * @Author: zqz
// * @Description:
// * @Date: Created in 10:33 2021/12/2
// */
//@Component
//@Slf4j
//public class MessageProducers implements InitializingBean, DisposableBean {
//
//    @Autowired
//    private MessageQueue messageQueue;
//    @Autowired
//    private ProcessMessageService processMessageService;
//    @Autowired
//    private UserService userService;
//
//    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
//            2,
//            Executors.defaultThreadFactory(),
//            new ThreadPoolExecutor.CallerRunsPolicy());
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        processBefore();
//        executor.scheduleWithFixedDelay(new MessageTask(messageQueue, processMessageService), 1, 5, TimeUnit.SECONDS);
//    }
//
//    private void processBefore() {
//        log.info("do processBefore...");
//        List<User> users = userService.getAll();
//        if (!users.isEmpty()) {
//            users.forEach(u -> {
//                userService.deleteById(u.getId());
//                Message message = new Message();
//                message.setType("Order");
//                message.setContent(JSON.toJSONString(u));
//                messageQueue.push(message);
//            });
//        }
//    }
//
//
//    @Override
//        public void destroy() throws Exception {
//        log.info("MessageConsumer shutdown ...");
//        boolean flag = true;
//        while (flag) {
//            Message message = messageQueue.pull();
//            if (null != message) {
//                String content = message.getContent();
//                String name = Optional.ofNullable(content).map(JSON::parseObject).map(j -> j.getString("name")).orElse(null);
//                Integer age = Optional.ofNullable(content).map(JSON::parseObject).map(j -> j.getInteger("age")).orElse(0);
//                String sex = Optional.ofNullable(content).map(JSON::parseObject).map(j -> j.getString("sex")).orElse(null);
//                String address = Optional.ofNullable(content).map(JSON::parseObject).map(j -> j.getString("address")).orElse(null);
//                User user = new User();
//                user.setName(name);
//                user.setAge(age);
//                user.setSex(sex);
//                user.setAddress(address);
//                userService.insert(user);
//            } else {
//                flag = false;
//            }
//        }
//        executor.shutdown();
//    }
//}
