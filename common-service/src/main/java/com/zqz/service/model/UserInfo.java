package com.zqz.service.model;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 16:02 2020/8/27
 */
public class UserInfo {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 1, max = 5, message = "长度不能小于1或者大于5")
    private String name;

    @Min(value = 1, message = "年龄最小值不能小于1")
    @Max(value = 50, message = "年龄最大值不能大于50")
    private Integer age;

    @NotNull(message = "金额不能为空")
    private BigDecimal amt;

    @Email(message = "邮箱格式错误")
    private String email;

    @Size(min = 1, max = 3, message = "地址个数不能小于1或者大于3")
    @ApiModelProperty(value = "地址集合")
    private List<String> address;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", amt=" + amt +
                ", email='" + email + '\'' +
                ", address=" + address +
                '}';
    }
}
