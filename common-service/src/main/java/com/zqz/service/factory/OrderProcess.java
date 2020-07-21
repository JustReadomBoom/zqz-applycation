package com.zqz.service.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:18 AM 2020/7/21
 */
public class OrderProcess implements ProcessService{
    private Logger log = LoggerFactory.getLogger(OrderProcess.class);

    private String type;

    public OrderProcess(String type) {
        this.type = type;
    }

    public String process() {
        log.info("下单业务逻辑处理");
        return "业务类型:" + type;
    }
}
