package com.zqz.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:21 AM 2020/7/21
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * @Author: zqz
     * @Description: 通用异常
     * @Param: [e]
     * @Return: com.zqz.service.exception.ResultBean
     * @Date: 11:29 AM 2020/7/21
     */
    @ExceptionHandler(Throwable.class)
    public ResultBean commonException(Throwable e) {
        return handlerException(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }


    /**
     * @Author: zqz
     * @Description: 自定义异常
     * @Param: [e]
     * @Return: com.zqz.service.exception.ResultBean
     * @Date: 11:29 AM 2020/7/21
     */
    @ExceptionHandler(ProcessException.class)
    public ResultBean customException(ProcessException e) {
        return handlerException(HttpStatus.ALREADY_REPORTED, e);

    }


    private ResultBean handlerException(HttpStatus httpStatus, Throwable e) {
        return new ResultBean(httpStatus, e.getMessage());
    }
}
