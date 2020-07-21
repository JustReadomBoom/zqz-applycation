package com.zqz.app.consumer.controller;

import com.zqz.app.service.SaveResp;
import com.zqz.app.service.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:46 AM 2020/7/10
 */
@RestController
@RequestMapping("/consumer")
public class UserController {

    //多个方法调用只需改一处就ok
    private static final String URL_PREFIX = "http://localhost:8001";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") Long id) {
        //调用远程服务 http请求
        String url = URL_PREFIX + "/provider/user/" + id;
        return restTemplate.getForObject(url, User.class);
    }

    @GetMapping("/user/save")
    public SaveResp saveUser(@RequestParam("id") Integer id){
        String url = URL_PREFIX + "/provider/save/user";
        User user = new User();
        user.setId(id);
        user.setName("张无忌");
        return restTemplate.postForObject(url, user, SaveResp.class);
    }
}
