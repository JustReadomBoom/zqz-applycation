package com.zqz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.SftpATTRS;
import com.zqz.service.enums.KeyStoreTypeEnum;
import com.zqz.service.lambda.Student;
import com.zqz.service.model.UserInfo;
import com.zqz.service.utils.FileUtil1;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 14:33 2020/10/23
 */
@Slf4j
public class CommonTest {


    @Test
    public void testMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zqz");
        map.put("age", 20);
        map.put("address", "GZ");

        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            Object value = next.getValue();
            System.out.println("key: " + key);
            System.out.println("value: " + value);
        }

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("key: " + key);
            System.out.println("value: " + value);
        }

    }

    @Test
    public void test2() {
//        BigDecimal amt = new BigDecimal("20.75");
//        System.out.println(amt.intValue());
//        System.out.println(amt.setScale(0, BigDecimal.ROUND_HALF_UP));
        String json = "{\n" +
                "            \"creditResult\":{\n" +
                "                  \"value\":\"zhouqizhi\",\n" +
                "                  \"index\":23\n" +
                "             }\n" +
                "        }";
        JSONObject dataJson = JSON.parseObject(json);

//        String creditResult = dataJson.getJSONObject("creditResult").getString("value");
        String a = Optional.of(dataJson).map(Obj -> Obj.getJSONObject("creditResult")).map(S -> S.getString("value")).orElse(null);
        System.out.println(a);

        String filePath = "/Users/zhouqizhi/Desktop/index.jpg";
        int index = filePath.lastIndexOf(".");
        String substring = filePath.substring(filePath.lastIndexOf("."));
        System.out.println(substring);

        Hashtable<String, Object> hashtable = new Hashtable<>();
        hashtable.put("name", "zhouqizhi");
        Map<String, Object> map = new HashMap<>();
        map.put("age", 27);
        map.put("add", "GZ");
        hashtable.putAll(map);
        System.out.println(JSON.toJSONString(hashtable));


    }


    @Test
    public void test3() {
        String data = "123,345,789,222,908";
        String[] array = data.split(",");
        List<String> list = new ArrayList<>(data.length());
        Collections.addAll(list, array);
        System.out.println(JSON.toJSONString(list));
    }


    @Test
    public void testThread() {
        final Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("This is t1 ... ");
            }
        });

        final Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("This is t2 ... ");
            }
        });

        final Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("This is t3 ... ");
            }
        });

        t3.start();
        t2.start();
        t1.start();


    }


    class MyTestThread extends Thread {
        volatile boolean stop = false;

        @Override
        public void run() {
            while (!stop) {
                System.out.println(getName() + " is running...");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("week up from block...");
                    stop = true;
                }
            }
            System.out.println(getName() + " is exiting...");
        }
    }

    @Test
    public void testMyThread() throws InterruptedException {
        MyTestThread m1 = new MyTestThread();
        System.out.println("Starting thread...");
        m1.start();
        Thread.sleep(3000);
        System.out.println("Interrupt thread..." + m1.getName());
        m1.interrupt();
        Thread.sleep(3000);
        System.out.println("Stopping application...");
    }


    @Test
    public void testJson() {
        UserInfo info = new UserInfo();
        info.setName("zhouqizhi");
        info.setAge(25);
        info.setAmt(new BigDecimal(600000));
        info.setEmail("88888@163.com");
        List<String> list = new ArrayList<>();
        list.add("9999");
        list.add("7777");
        list.add("3333");
        info.setAddress(list);

        List<String> list2 = list.parallelStream().filter(s -> new BigDecimal(s).compareTo(new BigDecimal("6666")) > 0).collect(Collectors.toList());
        System.out.println(list2);

    }

    @Test
    public void testRequest() throws Exception {
        String path = "/Users/zhouqizhi/Desktop/未入数据库客户(1).xlsx";
        String content = FileUtil1.readFileByLines(path);
        System.out.println(content);


    }


    @Test
    public void testCompletableFuture() throws InterruptedException {
        //异步串行执行任务，下一个任务参数使用上一个任务的返回结果
        CompletableFuture<Student> future1 = CompletableFuture.supplyAsync(() -> taskOne("Lebron james"));

        CompletableFuture<Integer> future2 = future1.thenApplyAsync((this::taskTwo));

        CompletableFuture<String> future3 = future2.thenApplyAsync(this::taskThree);

        future3.thenAccept(System.out::println);

        //主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭
        Thread.sleep(200);
    }



    private Student taskOne(String name){
        return new Student(name, "男", 36);
    }

    private Integer taskTwo(Student student){
        return student.getAge();
    }

    private String taskThree(Integer age){
        return "Age is " + age;
    }


    @Test
    public void testLocalDateTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = now.format(formatter);
        System.out.println(format);

        Student s = null;
        Set<Integer> list = new HashSet<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);
        list.add(11);
        list.forEach(e -> {
            System.out.println(e.hashCode());
        });



    }


}
