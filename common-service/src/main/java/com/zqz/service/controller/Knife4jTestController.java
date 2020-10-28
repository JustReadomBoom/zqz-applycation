package com.zqz.service.controller;

import com.zqz.service.model.GetPersonReq;
import com.zqz.service.model.GetPersonResp;
import com.zqz.service.model.WebResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 09:34 2020/10/28
 */
@Api(tags = "Knife4j测试")
@RestController
@RequestMapping("/knife4j")
public class Knife4jTestController {


    @ApiOperation(value = "测试hello")
    @GetMapping("/test")
    public ResponseEntity<String> sayHi(@RequestParam("name")String name){
        return ResponseEntity.ok("Hi:"+name);
    }

    @ApiOperation(value = "获取个人信息")
    @PostMapping("/personInfo")
    public WebResp<GetPersonResp> getPersonInfo(@RequestBody GetPersonReq req){
        WebResp<GetPersonResp> resp = new WebResp<>();
        resp.setCode("0000");
        resp.setMsg("SUCCESS");
        GetPersonResp personResp = new GetPersonResp();
        personResp.setName("zqz");
        personResp.setAge(25);
        resp.setData(personResp);
        return resp;
    }
}
