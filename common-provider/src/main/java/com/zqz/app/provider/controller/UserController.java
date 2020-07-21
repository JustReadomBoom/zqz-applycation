package com.zqz.app.provider.controller;

import com.zqz.app.service.SaveResp;
import com.zqz.app.service.User;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:38 AM 2020/7/10
 */
@RestController
@RequestMapping("/provider")
public class UserController {

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") Integer id){
        User user = new User();
        user.setId(id);
        user.setName("zqz");
        return user;
    }

    @PostMapping("/save/user")
    public SaveResp saveUser(@RequestBody User user){
        SaveResp resp = new SaveResp();
        resp.setName(user.getName());
        resp.setAddress("深圳市福田区XXXXXX街道");
        resp.setAge("27");
        resp.setSex("男");
        return resp;
    }
}
