package com.zqz.service.stream;

import com.alibaba.fastjson.JSON;
import com.zqz.service.utils.RandomUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: TestStream
 * @Date: Created in 15:20 2022/4/11
 */
public class TestStream {


    @Test
    public void test1() {
        List<StudentOne> list1 = new ArrayList<>();
        List<StudentTwo> list2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            StudentOne s1 = new StudentOne();
            s1.setId(i + 1);
            s1.setName("张三" + (i + 1));
            list1.add(s1);
        }
        StudentTwo s21 = new StudentTwo();
        StudentTwo s22 = new StudentTwo();
        StudentTwo s23 = new StudentTwo();
        StudentTwo s24 = new StudentTwo();

        s21.setSId(2);
        s21.setAddress(RandomUtil.getRandomString(12));

        s22.setSId(2);
        s22.setAddress(RandomUtil.getRandomString(12));

        s23.setSId(3);
        s23.setAddress(RandomUtil.getRandomString(12));

        s24.setSId(4);
        s24.setAddress(RandomUtil.getRandomString(12));

        list2.add(s21);
        list2.add(s22);
        list2.add(s23);
        list2.add(s24);

        System.out.println("StudentOne:" + JSON.toJSONString(list1));
        System.out.println("StudentTwo:" + JSON.toJSONString(list2));

        System.out.println("=======================================");

        Map<Integer, List<StudentTwo>> collect = list2.stream().collect(Collectors.groupingBy(StudentTwo::getSId));
        System.out.println("list2 collect:" + JSON.toJSONString(collect));

        list1.forEach(l ->{
            if(collect.containsKey(l.getId())){
                l.setAddress(collect.get(l.getId()));
            }
        });

        System.out.println("result:" + JSON.toJSONString(list1));



    }
}
