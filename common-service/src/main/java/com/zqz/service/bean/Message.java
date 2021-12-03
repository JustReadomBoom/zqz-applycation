package com.zqz.service.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:24 2021/12/2
 */
@Data
public class Message implements Serializable {
    private static final long serialVersionUID = -5468221777269655764L;

    private String type;

    private String content;
}
