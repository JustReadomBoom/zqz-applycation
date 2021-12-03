package com.zqz.service.bean;

import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:25 2021/11/29
 */
@Data
public class CommonReq<T> implements Serializable {


    private static final long serialVersionUID = 7266933933030560183L;

    private String code;

    @Valid
    private T data;
}
