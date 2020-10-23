package com.zqz.service.model;

import java.io.Serializable;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:23 2020/10/23
 */
public class ApiResult implements Serializable {
    private static final long serialVersionUID = 8333370910865551632L;
    private Integer code;

    private String message;

    private Object data;

    public ApiResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
