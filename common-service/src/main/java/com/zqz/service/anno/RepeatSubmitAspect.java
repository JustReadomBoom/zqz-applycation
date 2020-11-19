package com.zqz.service.anno;

import com.zqz.service.model.ApiResult;
import com.zqz.service.redis.RedisLock;
import com.zqz.service.utils.RandomUtil;
import com.zqz.service.utils.RequestUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:20 2020/10/23
 */
@Aspect
@Component
public class RepeatSubmitAspect {
    private static final Logger log = LoggerFactory.getLogger(RepeatSubmitAspect.class);

    @Autowired
    private RedisLock redisLock;

    @Pointcut("@annotation(noRepeatSubmit)")
    public void pointCut(NoRepeatSubmit noRepeatSubmit) {
    }

    @Around("pointCut(noRepeatSubmit)")
    public Object around(ProceedingJoinPoint pjp, NoRepeatSubmit noRepeatSubmit) throws Throwable {
        int lockSeconds = noRepeatSubmit.lockTime();

        ServletRequestAttributes servletRequestAttributes = RequestUtil.getRequest();
        if(null == servletRequestAttributes){
            return new ApiResult(200, "request can not null", null);
        }

        HttpServletRequest request = servletRequestAttributes.getRequest();

        // 此处可以用token或者JSessionId
        String token = request.getHeader("Authorization");
        String path = request.getServletPath();
        String key = getKey(token, path);
        String value = getClientId();

        boolean isSuccess;

        try {
            isSuccess = redisLock.reqTryLock(key, value, lockSeconds);
        }catch (Exception e){
            log.error("*****tryLock异常:[{}]", e.getMessage(), e);
            return null;
        }
        if (isSuccess) {
            // 获取锁成功
            Object result;
            try {
                // 执行进程
                result = pjp.proceed();
            } finally {
                // 解锁
                redisLock.reqReleaseLock(key, value);
            }
            return result;
        } else {
            // 获取锁失败，认为是重复提交的请求
            return new ApiResult(200, "请求重复，请稍后再试", null);
        }

    }

    private String getKey(String token, String path) {
        return token + path;
    }

    private String getClientId() {
        return RandomUtil.createRandom(true, 12);
    }

}
