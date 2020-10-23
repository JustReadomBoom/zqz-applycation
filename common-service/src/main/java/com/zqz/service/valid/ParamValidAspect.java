package com.zqz.service.valid;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.groups.Default;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 15:26 2020/8/27
 */
@Aspect
@Component
public class ParamValidAspect {

    private static final Logger log = LoggerFactory.getLogger(ParamValidAspect.class);

    @Resource
    private LocalValidatorFactoryBean localValidatorFactoryBean;


    /**
     * 第1个*：表示任意返回值
     * 第2个*：类下面的所有方法
     * 第3个*：方法里面的所有指定注解
     * 第4个*：注解对应的对象，即需要校验的参数体
     * 对于多个controller, 使用||增加
     */
//    @Pointcut(value = "execution(public * com.zqz.service.controller.*.*(@com.zqz.service.valid.ParamValid (*)))")
    @Pointcut(value = "execution(public * com.zqz.service..*.*(@com.zqz.service.valid.ParamValid (*)))")
    public void webLog() {}


    @Before("webLog()")
    public void before(JoinPoint joinPoint) throws Exception {
        doBefore(joinPoint);
    }

    /**
     * 参数校验
     * @param joinPoint
     */
    private void doBefore(JoinPoint joinPoint) {
        Object paramValue = getMethodParamValue(joinPoint);
        if (paramValue != null) {
            Set<ConstraintViolation<Object>> validErrors = this.localValidatorFactoryBean.validate(paramValue, new Class[]{Default.class});
            Iterator iterator = validErrors.iterator();
            StringBuilder errorMsg = new StringBuilder();

            while (iterator.hasNext()) {
                ConstraintViolation constraintViolation = (ConstraintViolation) iterator.next();
                String error = constraintViolation.getPropertyPath() + ":" + constraintViolation.getMessage();
                errorMsg.append(iterator.hasNext() ? error + "; " : error);
            }
            if (!validErrors.isEmpty()) {
                throw new ParamValidException(errorMsg.toString());
            }
        }
    }

    /**
     * 获取加了自定义参数校验注解的参数值
     * @param joinPoint
     * @return
     */
    private Object getMethodParamValue(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        /*获取@ParamValidate注解*/
        Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations();
        if (parameterAnnotations != null) {
            for (Annotation[] parameterAnnotation : parameterAnnotations) {
                int paramIndex = ArrayUtils.indexOf(parameterAnnotations, parameterAnnotation);
                for (Annotation annotation : parameterAnnotation) {
                    if (annotation instanceof ParamValid) {
                        return args[paramIndex];
                    }
                }
            }
        }
        return null;
    }



}
