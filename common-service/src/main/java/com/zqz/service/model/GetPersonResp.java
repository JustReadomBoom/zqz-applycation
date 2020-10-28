package com.zqz.service.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 09:58 2020/10/28
 */
@Data
@ApiModel(value = "获取个人信息响应")
public class GetPersonResp implements Serializable {

    @ApiModelProperty(value = "姓名", required = true, example = "小明")
    private String name;

    @ApiModelProperty(value = "年龄", example = "25")
    private Integer age;
}
