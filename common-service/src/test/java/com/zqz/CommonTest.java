package com.zqz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jcraft.jsch.SftpATTRS;
import com.zqz.service.enums.KeyStoreTypeEnum;
import com.zqz.service.lambda.Student;
import com.zqz.service.model.UserBean;
import com.zqz.service.model.UserInfo;
import com.zqz.service.utils.FileUtil1;
import com.zqz.service.utils.SeqUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.text.SimpleDateFormat;
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


    private Student taskOne(String name) {
        return new Student(name, "男", 36);
    }

    private Integer taskTwo(Student student) {
        return student.getAge();
    }

    private String taskThree(Integer age) {
        return "Age is " + age;
    }


    @Test
    public void testLocalDateTime() {
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

    @Test
    public void test4() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                5,
                Runtime.getRuntime().availableProcessors(),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(50),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 100; i++) {
            executor.submit(new MyTest4Task());
        }


    }

    class MyTest4Task implements Runnable {

        @Override
        public void run() {
            String name = SeqUtil.createShortSeq();
            System.out.println("thread=" + Thread.currentThread().getName() + " ,name=" + name);
        }
    }


    @Test
    public void test5() {
        List<String> drugList = Arrays.asList(
                "五维牛磺酸口服溶液", "替硝唑", "婴儿健脾散", "甲硝唑", "牡蛎碳酸钙", "呋喃妥因", "VB12", "呋喃唑酮", "VB2", "连蒲双清", "维生素AD", "盐酸小檗碱片", "维生素E", "复方地芬诺酯片", "烟酸", "药用炭", "烟酸片", "次硝酸铋", "叶酸", "盐酸咯哌丁胺", "密盖息", "苯丙醇", "硫酸氨基葡萄糖", "庆大霉素普鲁卡B12颗", "亚硫酸氢钠甲奈醌", "庆大霉素碳酸铋", "醋酸甲萘氢醌片", "复方芦荟胶囊", "盐酸小檗胺", "硫糖铝", "地芬尼多片", "雷尼替丁", "马来酸氯苯那敏", "固肠止泻丸", "茶苯海明", "鼠李铋镁片", "酚磺乙胺", "消旋山莨菪碱", "肾上腺色腙片", "蜡样芽胞杆菌制剂", "重酒石酸间羟胺", "胃得乐", "去氧肾上腺素", "复方铝酸铋", "多巴酚丁胺", "酚酞片", "硫喷妥钠", "大黄碳酸氢钠片", "丁卡因", "碳酸氢钠", "酚妥拉明", "阿苯达唑片", "消栓灵", "哌嗪", "氯氮卓", "复方氢氧化铝片", "盐酸氯丙嗪", "维u颠茄镁铝胶囊", "多塞平", "西咪替丁", "地西泮", "甲氧氯普胺", "丙戊酸钠", "盐酸托哌酮", "异丙嗪", "维拉帕米片", "苯巴比妥", "吡拉西坦片", "艾司唑仑", "曲克芦丁片", "阿普唑仑", "马来酸噻吗洛尔", "黛力新", "罗通定片", "甲丙氨酯", "盐酸普罗帕酮", "苯海索", "盐酸普萘洛尔", "苯妥英钠", "双嘧达莫", "喷他佐辛", "复方硫酸双肼屈嗪片", "布桂嗪", "门冬氨酸钾镁", "间羟胺", "美托洛尔", "香丹注射液", "美西律", "人免疫球蛋白", "三磷酸腺苷二钠", "氯化钠注射液", "卡托普利", "葡萄糖氯化钠注射液", "复方三维亚油酸", "葡萄糖注射液", "桂利嗪片", "复方氯化钠注射液");


        System.out.println(JSON.toJSONString(drugList.size()));
        System.out.println(randomNum(1, 5));


    }

    public int randomNum(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }


    @Test
    public void testTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MONTH, -1);

    }

    @Test
    public void testRedis() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testDownloadFile() {
        try {
//            InputStream stream = getInputStreamByUrl("https://bucket-r.oss-cn-beijing.aliyuncs.com/sxdsj/doctor/img/1496325596899737600/b76d4fe1-3997-4da9-ba27-5c78c7ab401e.jpg");
//            String dir = "E:\\temp\\test1.jpg";
//            writeToLocal(dir, stream);

            String data = "k9pAOFAJdlW9tgmgKNbRX3nfaCxLrXVzNzW/0xssInYgwBp0qEj0v4oM cv2Cbpw76QdSpYtW/xNmx7O8ZGIqsSizhYuUOXc7rUTRqe6M6NjrgwK1tA37FcSeRB8y2WFNc4pgtBNL0VKFxAnsgOo1MiA";

            String encode = URLEncoder.encode(data, "utf-8");
            System.out.println("encode = " + encode);
            String decode = URLDecoder.decode(encode, "utf-8");
            System.out.println("decode = " + decode);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 文件流写入本地目录
     *
     * @param destination
     * @param input
     * @throws IOException
     */
    private static void writeToLocal(String destination, InputStream input)
            throws IOException {
        int index;
        byte[] bytes = new byte[1024];
        FileOutputStream downloadFile = new FileOutputStream(destination);
        while ((index = input.read(bytes)) != -1) {
            downloadFile.write(bytes, 0, index);
            downloadFile.flush();
        }
        downloadFile.close();
        input.close();
    }

    public static InputStream getInputStreamByUrl(String fileUrl) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(fileUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(20 * 1000);
            ByteArrayOutputStream outPut = new ByteArrayOutputStream();
            IOUtils.copy(connection.getInputStream(), outPut);
            return new ByteArrayInputStream(outPut.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != connection) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Test
    public void testTimesTamp() throws Exception {
        Date now = new Date();
        long startTime = now.getTime();
        System.out.println(startTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE, 8);
        Date date = calendar.getTime();
        long endTime = date.getTime();
        System.out.println(endTime);
        System.out.println(endTime > startTime);
        Long rTime = endTime - startTime;
        System.out.println(rTime);
        System.out.println(rTime / (1000 * 60));


    }


    @Test
    public void testL() {
        Set<String> set = new HashSet<>();
        set.add("a123");
        set.add("b123");
        set.add("c123");
        set.add("d123");
        set.add("e123");
        set.add("f123");
        List<String> list1 = new ArrayList<>();
        list1.add("a123");
        list1.add("b123");
        list1.add("c123");
        list1.add("d123");
        list1.add("e123");
        list1.add("f123");
        List<String> d = list1.stream().filter(l -> l.contains("d")).collect(Collectors.toList());
        System.out.println(d);
        List<String> e = set.stream().filter(s -> s.contains("e")).collect(Collectors.toList());
        System.out.println(e);


    }


}
