package com.zqz.service.lambda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:15 2021/1/18
 */
@Data
@AllArgsConstructor
@ToString
public class Student {
    private String name;
    private String sex;
    private Integer age;
}
