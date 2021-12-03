package com.zqz.service.controller;

import com.zqz.service.LocalQueue;
import com.zqz.service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 3:01 PM 2020/7/17
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private LocalQueue queue;



    @GetMapping("/add")
    public void addUser() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName(getRandomString(5));
            user.setAge(random.nextInt());
            user.setSex(getRandomString(1));
            user.setAddress(getRandomString(10));
            queue.add(user);
        }


    }


    public static String getRandomString(int length) {

        //定义一个字符串（A-Z，a-z，0-9）即62位；
        String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //由Random生成随机数
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        //长度为几就循环几次
        for (int i = 0; i < length; ++i) {
            //产生0-61的数字
            int number = random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }





}
