package com.zqz.service.controller;

import com.alibaba.fastjson.JSON;
import com.zqz.service.bean.CommonReq;
import com.zqz.service.bean.TestParamReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:24 2021/11/29
 */
@RequestMapping("/param")
@RestController
@Slf4j
public class ParamChkController {




    @PostMapping("/test/one")
    public String testOne(@Valid @RequestBody CommonReq<TestParamReq> req){
        log.info("req:[{}]", JSON.toJSONString(req));
        return "OK";
    }
}
