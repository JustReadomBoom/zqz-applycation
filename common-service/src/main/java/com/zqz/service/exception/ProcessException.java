package com.zqz.service.exception;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:30 AM 2020/7/21
 */
public class ProcessException extends RuntimeException{

    public ProcessException(String msg){
        super(msg);
    }

    public ProcessException(){
        super();
    }
}
