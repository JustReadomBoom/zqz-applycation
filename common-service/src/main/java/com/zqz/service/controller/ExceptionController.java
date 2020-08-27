package com.zqz.service.controller;

import com.zqz.service.exception.ResultBean;
import com.zqz.service.model.UserInfo;
import com.zqz.service.valid.ParamValid;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:32 AM 2020/7/21
 */
@RestController
@RequestMapping("/exception")
public class ExceptionController {


    @GetMapping("/common")
    public ResultBean testCommonException() {
        throw new RuntimeException("Common exception here...");
    }

    @GetMapping("/processes")
    public ResultBean testProcessException() {
        String a = null;
        Assert.notNull(a, "a不能为空，异常");
//        throw new ProcessException("Process exception here...");
        return null;
    }

    @PostMapping("/param")
    public Object testParam(@RequestBody @ParamValid UserInfo userInfo){
        return userInfo.toString();
    }
}
