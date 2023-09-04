package com.ksyun.train.controller;

import com.ksyun.train.common.CountLimitException;
import com.ksyun.train.config.RateLimiterConfig;
import jdk.nashorn.internal.ir.RuntimeNode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * @Author : zhaji
 * @create 2023/7/17 20:57
 */
@Aspect
@Component
@Slf4j
public class RateLimiterAspect {
    @Resource
    private RedisTemplate redisTemplate;
    //定义切点
//    @Pointcut("@annotation(com.ksyun.train.controller.RateLimiterAnnotation)")
//    public void pointcut(){
//
//    }

    //定义环绕通知
//    @Before("@annotation(rateLimiterAnnotation) && args(request)")
    @Before("@annotation(rateLimiterAnnotation)")
    public void applyRateLimit(JoinPoint joinPoint,RateLimiterAnnotation rateLimiterAnnotation) throws Throwable {
        String userId =requestUserId();
        log.info(userId);
        String apiName = joinPoint.getSignature().getName();
        log.info(apiName);
        String key = userId+":"+apiName;
        int requestCount = getRequestCount(key);
        int limitCount = RateLimiterConfig.getQpsLimit(userId, apiName);
        if(requestCount >= limitCount){
            throw new CountLimitException();
        }
        //对给定的key进行递增操作
        redisTemplate.opsForValue().increment(key);
        //使用RedisTemplate来设置键的过期时间的操作
        redisTemplate.expire(key,rateLimiterAnnotation.ttl(), TimeUnit.SECONDS);
//        return joinPoint.proceed();
    }
    //获取用户ID
    private String requestUserId() throws IOException {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();

        return request.getHeader("X-KSC-ACCOUNT-ID");
    }
    private int getRequestCount(String key){
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null && value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                // 处理转换异常，例如打印日志或其他逻辑
                e.printStackTrace();
            }
        }

        return 0; // 默认返回0，表示无法获取有效的请求计数或发生了转换异常
    }


}
