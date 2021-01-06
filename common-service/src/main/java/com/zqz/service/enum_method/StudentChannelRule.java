package com.zqz.service.enum_method;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 13:44 2020/12/24
 */
public class StudentChannelRule implements GeneralChannelRule {
    @Override
    public void process() {
        System.out.println("This is student channel rule...");
    }
}
