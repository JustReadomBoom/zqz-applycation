package com.zqz.service.batch;

import com.zqz.service.entity.OrderRecord;
import com.zqz.service.mapper.OrderRecordService;
import com.zqz.service.utils.RandomUtil;
import com.zqz.service.utils.SeqUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 13:54 2021/11/24
 */
@Service
@Slf4j
public class BatchService {

    @Autowired
    private OrderRecordService orderRecordService;

    private List<OrderRecord> dataList = new ArrayList<>(1000000);

    private ExecutorService pool = new ThreadPoolExecutor(
            2,
            Runtime.getRuntime().availableProcessors(),
            2L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(3),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());


    public boolean doInsert() {
        try {
            createData();
            Future<Boolean> future = pool.submit(new BatchInsertTask(dataList, orderRecordService));
            return future.get();
        } catch (Exception e) {
            log.error("doInsert error:{}", e.getMessage(), e);
            return false;
        }
    }

    private void createData() {
        for (int i = 0; i < 1000000; i++) {
            OrderRecord r = new OrderRecord();
            r.setOrderId(SeqUtil.createShortSeq());
            r.setName(RandomUtil.getRandomString(10));
            r.setAddress(RandomUtil.getRandomString(20));
            r.setAmount(BigDecimal.TEN);
            dataList.add(r);
        }
    }

}
