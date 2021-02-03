package com.zqz.service.shutdownHook;

import org.junit.Test;


/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:37 2021/1/22
 */
public class HookTest {

    @Test
    public void test(){
        System.out.println("======= start ========");

        //业务处理。。。

        Thread hook = new Thread(this::demoTask);
        addShutdownHook(hook);
        System.out.println("======= end ========");
    }


    private void demoTask() {
        //shutdown 时执行的额外动作
        System.out.println("This is my shutdown hook demo ...");
    }



    private void addShutdownHook(Thread hook){
        SecurityManager manager = System.getSecurityManager();
        if(null != manager){
            manager.checkPermission(new RuntimePermission("shutdown hooks!!!"));
        }
        Runtime.getRuntime().addShutdownHook(hook);
    }
}
