package com.zqz.service.task;

import com.zqz.service.LocalQueue;
import com.zqz.service.entity.User;

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

    @Override
    public void run() {
        System.out.println("=====================");
        User user = localQueue.get();
        if (null != user) {
            System.out.println("获取用户:" + user.toString());
            System.out.println("剩余用户数量: " + localQueue.getCount());
        } else {
            System.out.println("无用户");
        }
    }
}
