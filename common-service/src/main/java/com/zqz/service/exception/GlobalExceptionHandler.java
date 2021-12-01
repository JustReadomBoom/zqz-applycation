package com.zqz.service.exception;

import com.zqz.service.valid.ParamValidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:21 AM 2020/7/21
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
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


    @ExceptionHandler(ParamValidException.class)
    public ResultBean paramValidException(ParamValidException e){
        String errorMsg = String.format("参数错误[%s]", e.getLocalizedMessage());
        log.error(errorMsg);
        return new ResultBean(HttpStatus.BAD_REQUEST, errorMsg);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map handlerExp(MethodArgumentNotValidException e){
        Map<String, String> map = new HashMap<>(2);
        map.put("message", e.getBindingResult().getFieldError().getDefaultMessage());
        map.put("code", "500");
        return map;
    }

}
