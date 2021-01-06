package com.zqz;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.data.redis.hash.HashMapper;

import java.util.*;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:27 2020/11/20
 */
@Slf4j
public class StringJoinerTest {


    @Test
    public void test1(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zhouqizhi");
        map.put("age", 27);
        map.put("local", true);
        map.put("address", "GZ");
        Integer age = (Integer) Optional.of(map).map(e -> map.get("age")).orElse(null);
//        StringJoiner joiner = new StringJoiner("&");
//        joiner.add("name");
//        joiner.add("age");
//        joiner.add("address");
//        joiner.add("phone");
//        log.info("拼接结果:{}", joiner.toString());
    }

    @Test
    public void test2(){
        String a = "123,478,290,234,881,276,503,064";
        StringTokenizer tokenizer = new StringTokenizer(a, ",", false);
        System.out.println(tokenizer.countTokens());
        while (tokenizer.hasMoreTokens()){
            String s = tokenizer.nextToken();
            System.out.println(s);
            if("880".equals(s)){
                System.out.println("PASS");
                return;
            }
        }
        System.out.println("REJECT");
    }


}
