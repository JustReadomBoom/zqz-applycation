package com.zqz.service.controller;

import com.zqz.service.exception.ProcessException;
import com.zqz.service.exception.ResultBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/process")
    public ResultBean testProcessException() {
        throw new ProcessException("Process exception here...");
    }
}
