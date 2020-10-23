package com.zqz.service.redis;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 2:22 PM 2020/7/21
 */
@Component
public class RedisLock {
    private static final Logger log = LoggerFactory.getLogger(RedisLock.class);

    private static final Long RELEASE_SUCCESS = 1L;
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    // 当前设置 过期时间单位, EX = seconds; PX = milliseconds
    private static final String SET_WITH_EXPIRE_TIME = "EX";
    // if get(key) == value return del(key)
    private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * @Author: zqz
     * @Description: 加锁
     * @Param: [lockKey, timeStamp]
     * @Return: boolean
     * @Date: 3:07 PM 2020/7/21
     */
    public boolean lock(String lockKey, String timeStamp) {
        if (stringRedisTemplate.opsForValue().setIfAbsent(lockKey, timeStamp)) {
            // 对应setnx命令，可以成功设置,也就是key不存在，获得锁成功
            log.info("加锁成功");
            return true;
        }
        //获取锁失败
        // 判断锁超时 - 防止原来的操作异常，没有运行解锁操作 ，防止死锁
        String currentLock = stringRedisTemplate.opsForValue().get(lockKey);
        //如果锁过期 currentLock不为空且小于当前时间
        if (!StringUtils.isBlank(currentLock) && Long.parseLong(currentLock) < System.currentTimeMillis()) {
            //如果lockKey对应的锁已经存在，获取上一次设置的时间戳之后并重置lockKey对应的锁的时间戳
            String preLock = stringRedisTemplate.opsForValue().getAndSet(lockKey, timeStamp);
            //假设两个线程同时进来这里，因为key被占用了，而且锁过期了。
            //获取的值currentLock=A(get取的旧的值肯定是一样的),两个线程的timeStamp都是B,key都是K.锁时间已经过期了。
            //而这里面的getAndSet一次只会一个执行，也就是一个执行之后，上一个的timeStamp已经变成了B。
            //只有一个线程获取的上一个值会是A，另一个线程拿到的值是B。
            if (!StringUtils.isBlank(preLock) && preLock.equals(currentLock)) {
                log.info("重置过期时间成功");
                return true;
            }
        }
        log.info("加锁失败");
        return false;
    }

    /**
     * @Author: zqz
     * @Description: 释放锁
     * @Param: [lockKey, timeStamp]
     * @Return: void
     * @Date: 3:07 PM 2020/7/21
     */
    public void release(String lockKey, String timeStamp) {
        try {
            String currentValue = stringRedisTemplate.opsForValue().get(lockKey);
            if (!StringUtils.isBlank(currentValue) && currentValue.equals(timeStamp)) {
                //删除锁状态
                log.info(stringRedisTemplate.opsForValue().getOperations().delete(lockKey) ? "释放锁成功" : "释放锁失败");
            }
        } catch (Exception e) {
            log.error("redis解锁异常[{}]", e.getMessage(), e);
        }
    }


    /**
     * 该加锁方法仅针对单实例 Redis 可实现分布式加锁
     * 对于 Redis 集群则无法使用
     * 支持重复，线程安全
     * @param lockKey   加锁键
     * @param clientId  加锁客户端唯一标识(采用UUID)
     * @param seconds   锁过期时间
     * @return
     */
    public boolean reqTryLock(String lockKey, String clientId, long seconds) {
        return stringRedisTemplate.opsForValue().setIfAbsent(lockKey, clientId, seconds, TimeUnit.SECONDS);
    }

    /**
     * 与 tryLock 相对应，用作释放锁
     * @param lockKey
     * @param clientId
     * @return
     */
    public boolean reqReleaseLock(String lockKey, String clientId) {
        String currentValue = stringRedisTemplate.opsForValue().get(lockKey);
        if (!StringUtils.isBlank(currentValue) && currentValue.equals(clientId)) {
            //删除锁状态
            return stringRedisTemplate.opsForValue().getOperations().delete(lockKey);
        } else {
            return false;
        }
    }
}
