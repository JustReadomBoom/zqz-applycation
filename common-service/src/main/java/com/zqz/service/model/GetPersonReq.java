package com.zqz.service.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:01 2020/10/28
 */
@Data
@ToString
@ApiModel(value = "获取个人信息请求参数")
public class GetPersonReq  {

    @ApiModelProperty(value = "商户编号", required = true, example = "000001")
    private String mchNo;

    @ApiModelProperty(value = "数量", required = true)
    private Integer count;
}
