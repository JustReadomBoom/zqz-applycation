package com.zqz.service.transaction;

import com.zqz.service.mapper.GoodsStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:54 AM 2020/7/23
 */
@Service
public class TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private PlatformTransactionManager txManager;

    @Autowired
    private GoodsStoreService goodsStoreService;


    public void testTransaction(String flag) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        boolean commit = true;
        try {
            goodsStoreService.updateGoodsStore("100001", 20);
            if ("1".equals(flag)) {
                goodsStoreService.updateGoodsStore("100001", 1);
            } else {
                throw new RuntimeException("Update Exception!!!!!!");
            }
        } catch (Exception e) {
            log.error("测试事物出现异常:msg=[{}]", e.getMessage(), e);
            commit = false;
        } finally {
            if (commit) {
                txManager.commit(status);
            } else {
                txManager.rollback(status);
            }
        }
    }
}
