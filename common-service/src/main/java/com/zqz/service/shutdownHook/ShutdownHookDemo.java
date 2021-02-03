package com.zqz.service.shutdownHook;

import com.alibaba.fastjson.JSON;
import com.zqz.service.entity.GoodsStore;
import com.zqz.service.mapper.GoodsStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 14:03 2021/1/22
 */
@Component
@Slf4j
public class ShutdownHookDemo implements InitializingBean, DisposableBean {

    @Autowired
    private GoodsStoreService goodsStoreService;


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Application start now...");
    }




    @Override
    public void destroy() throws Exception {
        log.info("-----> Application shutdown start...");
        //应用销毁时做的动作
        GoodsStore store = goodsStoreService.getGoodsStore("100001");
        log.info("-----> store result: {}", JSON.toJSONString(store));
        log.info("-----> Application shutdown end.");
    }



}
