package com.zqz;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 9:50 AM 2020/7/23
 */
public class MapTest {


    @Test
    public void test1(){
        String key = "a";
        Map<String, Object> map = new HashMap<>();
        map.put(key, "123");
        map.compute(key, (s, v) -> null == v ? "hello" : v + "_hello");
        System.out.println(map);
    }
}
