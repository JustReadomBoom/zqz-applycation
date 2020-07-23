package com.zqz.service.utils;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:18 AM 2020/7/23
 */
public class DateUtil {

    private static ThreadLocal<SimpleDateFormat> format1 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private static ThreadLocal<SimpleDateFormat> format2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };

    private static ThreadLocal<SimpleDateFormat> format3 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };


    public static String getDateFormat1Str(Date date) {
        return format1.get().format(date);
    }

    public static String getDateFormat2Str(Date date) {
        return format2.get().format(date);
    }

    public static String getDateFormat3Str(Date date) {
        return format3.get().format(date);
    }

    public static String getNowDate() {
        LocalDate now = LocalDate.now();
        return now.toString();
    }

    public static LocalDate getBeforeDate() {
        LocalDate now = LocalDate.now();
        return now.minusDays(1);
    }

    public static LocalDate getLastDate() {
        LocalDate now = LocalDate.now();
        return now.plusDays(1);
    }

    public static String getNowToDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return pattern.format(now);
    }

    public static String getLastTimeStr(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return format1.get().format(calendar.getTime());
    }

    public static String getZeroTimeStr(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return format1.get().format(calendar.getTime());
    }


    public static void main(String[] args) {
        Date now = new Date();
        System.out.println(getDateFormat1Str(now));
        System.out.println(getDateFormat2Str(now));
        System.out.println(getDateFormat3Str(now));
        System.out.println(getNowDate());
        System.out.println(getBeforeDate());
        System.out.println(getLastDate());
        System.out.println(getNowToDate());
        System.out.println(getLastTimeStr(now));
        System.out.println(getZeroTimeStr(now));
    }

}
