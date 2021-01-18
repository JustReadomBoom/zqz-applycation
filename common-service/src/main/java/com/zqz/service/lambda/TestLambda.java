package com.zqz.service.lambda;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:23 2021/1/18
 */
public class TestLambda {


    @Test
    public void test1() {
        List<Student> list = Arrays.asList(
                new Student("jack", "male", 12),
                new Student("john", "male", 13),
                new Student("lily", "female", 11),
                new Student("david", "male", 14),
                new Student("luck", "female", 13),
                new Student("jones", "female", 15),
                new Student("han", "male", 13),
                new Student("alice", "female", 11),
                new Student("li", "male", 12)
        );

        Map<String, List<Student>> map1 = list.stream().
                sorted(Comparator.comparing(Student::getAge)).
                collect(Collectors.groupingBy(Student::getSex, Collectors.toList()));
        System.out.println(JSON.toJSONString(map1));


        Map<String, List<Student>> map2 = list.stream().
                sorted(Comparator.comparing(Student::getAge).
                        thenComparing(Student::getName)).
                collect(Collectors.groupingBy(Student::getSex, Collectors.toList()));
        System.out.println(JSON.toJSONString(map2));

    }
}
