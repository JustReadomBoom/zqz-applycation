package com.zqz.service.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:08 2020/10/23
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoRepeatSubmit {

    /**
     * 设置请求锁定时间 10s
     * @return
     */
    int lockTime() default 10;

}
