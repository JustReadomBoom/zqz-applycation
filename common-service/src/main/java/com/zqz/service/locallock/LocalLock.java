package com.zqz.service.locallock;

import java.lang.annotation.*;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 09:48 2021/1/6
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LocalLock {
    String key() default "";
}
