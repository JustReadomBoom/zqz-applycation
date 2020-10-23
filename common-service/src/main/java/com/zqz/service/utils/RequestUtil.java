package com.zqz.service.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:21 2020/10/23
 */
public class RequestUtil {
    public static ServletRequestAttributes getRequest() {
        return  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }
}
