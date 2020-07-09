package com.zqz.application.controller;

import com.zqz.application.RetryTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 5:56 PM 2020/7/7
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Autowired
    private RetryTestService retryTestService;
    @Autowired
    private DataSourceTransactionManager txManager;


    @GetMapping("/retry")
    public Object testRetry(@RequestParam Integer num) throws Exception{
        retryTestService.minGoodsnum(num);
        return "已发送";
    }


    @GetMapping("/transaction")
    public void testTransaction(){
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        boolean commit = true;
        try{
            retryTestService.minGoodsnum(0);
        }catch (Exception e){
            commit = false;
            log.error("");
        }finally {
            if(commit){
                txManager.commit(status);
            }else{
                txManager.rollback(status);
            }
        }
    }
}
