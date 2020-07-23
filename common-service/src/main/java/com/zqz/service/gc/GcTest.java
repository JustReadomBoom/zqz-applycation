package com.zqz.service.gc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 4:20 PM 2020/7/22
 */
public class GcTest {


    private static Map<String, AtomicInteger> map = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1),
                new ThreadPoolExecutor.DiscardOldestPolicy()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                map.compute(r.toString(), (s, atomicInteger) -> new AtomicInteger(atomicInteger == null ? 0 : atomicInteger.incrementAndGet()));
            }
        };

        for (int i = 0; i < 1000; i++) {
            executor.execute(new SimpleTask());
        }
        Thread.sleep(1000 * 2);
        System.out.println("map:" + map);
        executor.shutdownNow();


    }

    static class SimpleTask implements Runnable {

        @Override
        public void run() {
            System.out.println("SimpleTask execute success!");
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }

}
