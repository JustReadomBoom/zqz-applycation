package com.zqz.service.valid;

import java.lang.annotation.*;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 15:23 2020/8/27
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamValid {
}
