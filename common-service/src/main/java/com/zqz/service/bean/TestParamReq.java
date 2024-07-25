package com.zqz.service.bean;

import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:27 2021/11/29
 */
@Data
public class TestParamReq implements Serializable {
    private static final long serialVersionUID = 6296187968531414735L;

    @NotBlank(message = "name 不能为空")
    @Size(max = 5, message = "name长度超长")
    private String name;

    @NotNull(message = "age 不能为空")
    @Min(value = 20, message = "age不能小于20")
    @Max(value = 50, message = "age不能大于50")
    private Integer age;


    @NotNull(message = "amount不能为空")
    private BigDecimal amount;
}
