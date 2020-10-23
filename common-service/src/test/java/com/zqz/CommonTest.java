package com.zqz;

import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 14:33 2020/10/23
 */
public class CommonTest {


    @Test
    public void testMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zqz");
        map.put("age", 20);
        map.put("address", "GZ");

        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            Object value = next.getValue();
            System.out.println("key: " + key);
            System.out.println("value: " + value);
        }

        for(Map.Entry<String, Object> entry : map.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("key: " + key);
            System.out.println("value: " + value);
        }

    }

}
