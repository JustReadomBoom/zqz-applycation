package com.zqz.service.handler;

import com.zqz.service.LocalQueue;
import com.zqz.service.task.ProcessUserTask;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.concurrent.*;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 2:48 PM 2020/7/17
 */
@Component
public class ProcessUserHandler implements InitializingBean, DisposableBean {

    @Autowired
    private LocalQueue queue;

    private ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1, new ThreadPoolExecutor.DiscardOldestPolicy());

    public void afterPropertiesSet() throws Exception {
        executor.scheduleWithFixedDelay(new ProcessUserTask(queue), 1, 100, TimeUnit.SECONDS);
    }


    public void destroy() throws Exception {
        System.out.println("ProcessUserHandler------>销毁");
        executor.shutdown();
    }
}
