package com.zqz;

import com.alibaba.fastjson.JSON;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.zqz.service.ServiceApplication;
import com.zqz.service.aliyun.AliYunService;
import com.zqz.service.entity.OrderRecord;
import com.zqz.service.houseLoan.LoanDetailService;
import com.zqz.service.mapper.OrderRecordService;
import com.zqz.service.model.UserBean;
import com.zqz.service.utils.HttpUtil;
import com.zqz.service.utils.RandomUtil;
import com.zqz.service.utils.SeqUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 16:54 2020/8/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceApplication.class})
public class DemoTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private AliYunService aliYunService;

    @Autowired
    private LoanDetailService loanDetailService;

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

    @Test
    public void testSubmit() {
        String url = "http://localhost:7777/submit/test";
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    countDownLatch.await();
                    System.out.println("Thread:" + Thread.currentThread().getName() + ", time:" + System.currentTimeMillis());

                    UserBean bean = new UserBean();
                    bean.setUserId(UUID.randomUUID().toString());
                    Map<String, String> header = new HashMap<>();
                    header.put("Content-Type", "application/json");
                    header.put("Authorization", RandomUtil.createRandom(true, 12));

                    String resp = HttpUtil.postJson(url, header, JSON.toJSONString(bean));

                    System.out.println("RESP:" + Thread.currentThread().getName() + "," + resp);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        countDownLatch.countDown();
    }

    @Test
    public void testRedis() {
//        redisTemplate.opsForValue().set("myTestKey", 302.34);
        String key = "myTestKey";
        BigDecimal amt = new BigDecimal("20.64");
        redisTemplate.opsForValue().set(key, 200);
//        Long incr = redisTemplate.getConnectionFactory().getConnection().incrBy(redisTemplate.getStringSerializer().serialize(key), 10);
        redisTemplate.opsForValue().increment(key, amt.setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
        System.out.println(redisTemplate.opsForValue().get(key));

    }

    @Test
    public void testAliyun() {
        String filePath = "/Users/zhouqizhi/Desktop/index.jpg";
        aliYunService.uploadFile(filePath, "/demo");
    }


    @Test
    public void testInfluxDb() {
        String token = "wllPhoKuUjB40R0hOyIAxOw54vg1G4gDfyj1jmMt9i7sdVkCN9V1-ByVimdz6VMlx9MmXn88jcZeRGu6QCkEBQ==";
        String bucket = "test";
        String org = "forever";
        InfluxDBClient client = InfluxDBClientFactory.create("http://localhost:8086", token.toCharArray());

        Point point = Point
                .measurement("ZQZ_TEST")
                .addTag("name", "ZQZ")
                .addTag("age", "29")
                .addField("phoneType", "xsmax")
                .addField("addressDetail", "宜昌市伍家岗")
                .time(Instant.now(), WritePrecision.NS);
        WriteApiBlocking writeApi = client.getWriteApiBlocking();
        writeApi.writePoint(bucket, org, point);
    }

    @Test
    public void testQueryInfluxDB() {
        String org = "forever";
        String token = "wllPhoKuUjB40R0hOyIAxOw54vg1G4gDfyj1jmMt9i7sdVkCN9V1-ByVimdz6VMlx9MmXn88jcZeRGu6QCkEBQ==";
        InfluxDBClient client = InfluxDBClientFactory.create("http://localhost:8086", token.toCharArray());
        String query = "from(bucket:\"test\") |> range(start: 0) |> filter(fn: (r) => r._measurement == \"prescription\" and r._field == \"count\")";

        List<FluxTable> tables = client.getQueryApi().query(query, org);

        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {
                System.out.println("record = " + record.getValue());
            }
        }
    }

    @Test
    public void testLoanDetail() {
        loanDetailService.calculate();
    }

}
