package com.zqz.service.enum_method;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 13:46 2020/12/24
 */
public class TeacherChannelRule implements GeneralChannelRule{
    @Override
    public void process() {
        System.out.println("This is teacher channel rule...");
    }
}
