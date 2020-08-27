package com.zqz.service.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zqz
 * @Description: 序列号生成工具
 * @Date: Created in 11:13 AM 2019/12/20
 */
public class SeqUtil {

    private static final String PROCESS_NO_PRE = "MSXDHIPAC";
    private static final String TIME_FORMAT = "yyyyMMdd";
    private static final Integer RANDOM_LENGTH = 12;

    private static final FastDateFormat pattern = FastDateFormat.getInstance("yyyyMMddHHmmss");
    private static final AtomicInteger atomicInteger = new AtomicInteger(1);
    private static ThreadLocal<StringBuilder> threadLocal = new ThreadLocal<>();


    /**
     * @Author: zqz
     * @Description: 长码序列号生成（支持高并发）
     * @Param: []
     * @Return: java.lang.String
     * @Date: 9:28 AM 2020/2/13
     */
    public static String createLongSqp(){
        String lock = StringUtils.replace(UUID.randomUUID().toString(), "-", "");
        StringBuilder builder = new StringBuilder(pattern.format(Instant.now().toEpochMilli()));// 取系统当前时间作为订单号前半部分
        builder.append(Math.abs(lock.hashCode()));// HASH-CODE
        builder.append(atomicInteger.getAndIncrement());// 自增顺序
        threadLocal.set(builder);
        return threadLocal.get().toString();
    }


    /**
     * @Author: zqz
     * @Description: 短码序列号生成（支持高并发）
     * @Param: []
     * @Return: java.lang.String
     * @Date: 9:31 AM 2020/2/13
     */
    public static String createShortSeq(){
        String lock = StringUtils.replace(UUID.randomUUID().toString(), "-", "");
        StringBuilder builder = new StringBuilder(ThreadLocalRandom.current().nextInt(0,999));// 随机数
        builder.append(Math.abs(lock.hashCode()));// HASH-CODE
        builder.append(atomicInteger.getAndIncrement());// 自增顺序
        threadLocal.set(builder);
        return threadLocal.get().toString();
    }


    //1000个线程并发测试
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Set<String> set = new HashSet<>();
        FutureTask<String> task;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i <1000; i++) {
            Callable<String> callable = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println("当前线程:>>>>> ".concat(Thread.currentThread().getName()));
//                    return createShortSeq();
                    return createLongSqp();
                }
            };
            task = new FutureTask<>(callable);
            new Thread(task).start();
            System.out.println(task.get());
            set.add(task.get());
        }
        System.out.println("总共耗时:" + ((System.currentTimeMillis() - startTime)) + "ms");
        System.out.println("*************** " + set.size());
    }
}
