package com.zqz.service.valid;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 15:25 2020/8/27
 */
public class ParamValidException extends RuntimeException {
    public ParamValidException(String errorMsg) {
        super(errorMsg);
    }

    public ParamValidException(String errorMsg, Throwable e) {
        super(errorMsg, e);
    }

}
