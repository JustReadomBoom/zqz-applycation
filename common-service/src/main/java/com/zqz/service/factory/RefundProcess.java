package com.zqz.service.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:21 AM 2020/7/21
 */
public class RefundProcess implements ProcessService{
    private Logger log = LoggerFactory.getLogger(OrderProcess.class);
    private String type;

    public RefundProcess(String type){
        this.type = type;
    }

    public String process() {
        log.info("退货业务逻辑处理");
        return "业务类型:" + type;
    }
}
