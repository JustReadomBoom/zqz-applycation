package com.zqz;

import com.zqz.service.ServiceApplication;
import com.zqz.service.entity.OrderRecord;
import com.zqz.service.mapper.OrderRecordService;
import com.zqz.service.utils.RandomUtil;
import com.zqz.service.utils.SeqUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 16:54 2020/8/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceApplication.class})
public class DemoTest {

    private static final Logger log = LoggerFactory.getLogger(DemoTest.class);

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 8, 200, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(5));
    private Random random = new Random();


    @Autowired
    private OrderRecordService orderRecordService;


    @Test
    public void testBatch() {
        try {
            initData();
        } catch (Exception e) {
            log.error("Insert Batch Error:[{}]", e.getMessage(), e);
        }
    }


    private void initData() throws Exception {
        long startTime = System.currentTimeMillis();
        CreateDataTask task = new CreateDataTask();
        for (int i = 0; i < 10; i++) {
            log.info("------->Task[{}] Start......", i + 1);
            Future<List<OrderRecord>> future = executor.submit(task);
            List<OrderRecord> records = future.get();
            orderRecordService.insertBatch(records);
            log.info("线程池中线程数目:[{}], 队列中等待执行的任务数目:[{}], 已执行完别的任务数目:[{}]",
                    executor.getPoolSize(), executor.getQueue().size(), executor.getCompletedTaskCount());
        }
        long endTime = System.currentTimeMillis();
        log.info("During Time:[{}]ms", endTime - startTime);
    }


    class CreateDataTask implements Callable<List<OrderRecord>> {
        @Override
        public List<OrderRecord> call() throws Exception {
            List<OrderRecord> list = new ArrayList<>(100);
            for (int i = 0; i < 100; i++) {
                OrderRecord record = new OrderRecord();
                record.setOrderId(SeqUtil.createLongSqp());
                record.setAmount(new BigDecimal(RandomUtil.createRandom(true, 5)));
                record.setAddress(SeqUtil.createShortSeq());
                record.setName(SeqUtil.createShortSeq());
                list.add(record);
            }
            return list;
        }
    }
}
