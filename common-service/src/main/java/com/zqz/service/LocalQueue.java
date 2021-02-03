package com.zqz.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 2:34 PM 2020/7/17
 */
@Component
public class LocalQueue {

    private LinkedBlockingQueue<User> queue = new LinkedBlockingQueue<User>();

    public void add(User user) {
        try {
            System.out.println("新增用户:" + user.toString());
            this.queue.put(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User get() {
        try {
            if(!queue.isEmpty()){
                return queue.poll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getCount(){
        try{
            return queue.size();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
