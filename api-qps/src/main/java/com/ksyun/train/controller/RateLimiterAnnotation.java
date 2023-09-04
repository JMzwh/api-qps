package com.ksyun.train.controller;

import java.lang.annotation.*;

/**
 * @Author : zhaji
 * @create 2023/7/17 20:55
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiterAnnotation {

    // unit is second
    long ttl() default 10L;
}
