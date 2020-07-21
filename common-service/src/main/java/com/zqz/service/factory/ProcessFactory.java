package com.zqz.service.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:23 AM 2020/7/21
 */
public class ProcessFactory {

    private static Map<String, ProcessService> map = new HashMap<String, ProcessService>();

    static {
        map.put("order", new OrderProcess("order"));
        map.put("refund", new RefundProcess("refund"));
    }

    public static ProcessService getProcess(String type){
        return map.get(type);
    }
}
