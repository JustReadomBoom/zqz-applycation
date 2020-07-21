package com.zqz.service.exception;

import org.springframework.http.HttpStatus;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:24 AM 2020/7/21
 */
public class ResultBean {
    private HttpStatus httpStatus;
    private String msg;

    public ResultBean(HttpStatus httpStatus, String msg) {
        this.httpStatus = httpStatus;
        this.msg = msg;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
