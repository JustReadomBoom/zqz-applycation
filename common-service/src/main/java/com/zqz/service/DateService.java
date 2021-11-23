package com.zqz.service;

import java.text.SimpleDateFormat;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:35 AM 2020/7/22
 */
public class DateService {


    //线程安全
    private static final ThreadLocal<SimpleDateFormat> df = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };


    public static void main(String[] args) {
        //aaa
    }



}
