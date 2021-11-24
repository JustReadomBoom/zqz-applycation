package com.zqz.service.controller;

import com.zqz.service.batch.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 14:24 2021/11/24
 */
@RestController
@RequestMapping("/batch")
public class BatchController {
    @Autowired
    private BatchService batchService;


    //实测批量插入1000000条数据耗时50s
    @GetMapping("/start")
    public Object testBatchInsert() {
        return batchService.doInsert();
    }
}
