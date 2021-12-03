package com.zqz.service.batch;

import com.zqz.service.entity.OrderRecord;
import com.zqz.service.mapper.OrderRecordService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 14:04 2021/11/24
 */
@Slf4j
public class BatchInsertTask implements Callable<Boolean> {
    private static final int BATCH100 = 100;
    private List<OrderRecord> recordList;
    private OrderRecordService orderRecordService;
    private ThreadPoolExecutor POOL = new ThreadPoolExecutor(
            2,
            Runtime.getRuntime().availableProcessors(),
            2L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(3),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());


    public BatchInsertTask(List<OrderRecord> recordList, OrderRecordService orderRecordService) {
        this.recordList = recordList;
        this.orderRecordService = orderRecordService;
    }


    @Override
    public Boolean call() throws Exception {
        try {
            batchOp(recordList);
        } catch (Exception e) {
            log.error("BatchInsertTask call error:{}", e.getMessage(), e);
            return false;
        }
        return true;
    }


    private void batchOp(List<OrderRecord> list) {
        log.info("==========batchOp-我是线程:{}", Thread.currentThread().getName());
        if (!list.isEmpty()) {
            int size = list.size();
            if (size <= BATCH100) {
                orderRecordService.insertBatch(list);
            } else {
                batchOpSplit(list);
            }
        }
    }

    private void batchOpSplit(List<OrderRecord> list) {
        log.info("=========开始切割");
        Long t1 = System.currentTimeMillis();
        List<List<OrderRecord>> pList = pagingList(list);
        try {
            for (List<OrderRecord> fList : pList) {
                POOL.execute(() -> {
                    log.info("==========batchOpSplit-我是线程:{}", Thread.currentThread().getName());
                    batchOp(fList);
                });
            }
        } catch (Exception e) {
            log.error("batchOpSplit error:{}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        } finally {
            POOL.shutdown();
            Long t2 = System.currentTimeMillis();
            log.info("=========执行完成,用时:{}", t2 - t1);
        }

    }

    private static <T> List<List<T>> pagingList(List<T> list) {
        int pageSize = BATCH100;
        int length = list.size();
        int num = (length + pageSize - 1) / pageSize;
        List<List<T>> newList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            int fromIndex = i * pageSize;
            int toIndex = (i + 1) * pageSize < length ? (i + 1) * pageSize : length;
            newList.add(list.subList(fromIndex, toIndex));
        }
        return newList;
    }
}
