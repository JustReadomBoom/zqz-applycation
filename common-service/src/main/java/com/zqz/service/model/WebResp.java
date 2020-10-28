package com.zqz.service.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 14:19 2020/8/28
 */
@ApiModel(value = "前后端响应体")
@Accessors(chain = true)
@Data
@AllArgsConstructor
public class WebResp<T> implements Serializable {
    private static final long serialVersionUID = 8333370910865551632L;

    @ApiModelProperty(value = "响应描述", example = "处理成功")
    private String msg;

    @ApiModelProperty(value = "响应编码", example = "0000")
    private String code;

    private T data;

    public WebResp() {
        super();
        this.code = "0000";
        this.msg = "SUCCESS";
        this.data = null;
    }

    public WebResp(T data) {
        super();
        this.code = "0000";
        this.msg = "SUCCESS";
        this.data = data;
    }

    /** 返回异常 */
    public WebResp(Exception e, String msg) {
        super();
        this.msg = StringUtils.isNotBlank(msg) ? msg : "系统异常";
        this.code = "9999";
    }

    public WebResp(String code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }
}
