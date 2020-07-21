package com.zqz.service.biz;

import com.zqz.service.entity.GoodsStore;
import com.zqz.service.mapper.GoodsStoreService;
import com.zqz.service.redis.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 3:34 PM 2020/7/21
 */
@Service
public class GoodsStoreBizService {
    private static final Logger log = LoggerFactory.getLogger(GoodsStoreBizService.class);

    @Autowired
    private GoodsStoreService goodsStoreService;
    @Autowired
    private RedisLock redisLock;

    private static final int TIMEOUT = 5 * 1000;

    public String updateGoodsStore(String code, int count) {
        //上锁
        long time = System.currentTimeMillis() + TIMEOUT;
        if (!redisLock.lock(code, String.valueOf(time))) {
            return "排队人数太多，请稍后再试！";
        }
        log.info("获得锁时间戳:[{}]", String.valueOf(time));
        try {
            GoodsStore goodsStore = goodsStoreService.getGoodsStore(code);
            if(null != goodsStore){
                Integer store = goodsStore.getStore();
                if(store <= 0){
                    return String.format("对不起，已卖完，库存为:[%s]", store);
                }
                if(store < count){
                    return String.format("对不起，库存不足，库存为:[%s]，您购买的数量为:[%s]", store, count);
                }
                log.info("剩余库存:[{}]，扣除库存:[{}]", store, count);
                goodsStoreService.updateGoodsStore(code, count);
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return "恭喜您，购买成功！";
            } else {
                return "获取库存失败";
            }
        } finally {
            redisLock.release(code, String.valueOf(time));
            log.info("释放锁的时间戳:[{}]", String.valueOf(time));
        }
    }
}
