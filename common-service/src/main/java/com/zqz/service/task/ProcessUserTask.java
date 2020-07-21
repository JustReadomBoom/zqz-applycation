package com.zqz.service.task;

import com.zqz.service.LocalQueue;
import com.zqz.service.User;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 2:43 PM 2020/7/17
 */
public class ProcessUserTask implements Runnable {

    private LocalQueue localQueue;

    public ProcessUserTask(LocalQueue localQueue) {
        this.localQueue = localQueue;
    }

    public void run() {
        System.out.println("=====================");
        User user = localQueue.get();
        if (null != user) {
            System.out.println("获取用户:" + user.toString());
        } else {
            System.out.println("无用户");
        }
    }
}
