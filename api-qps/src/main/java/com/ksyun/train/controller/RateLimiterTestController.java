package com.ksyun.train.controller;

import com.ksyun.train.controller.RateLimiterAnnotation;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : zhaji
 * @create 2023/7/17 20:54
 */
@RestController
@EnableAspectJAutoProxy
public class RateLimiterTestController {

    @RateLimiterAnnotation
    @RequestMapping("/test/instance/query")
    public String describeInstance() {
        return "describe instance suqueryccess";
    }

    @RateLimiterAnnotation
    @RequestMapping("/test/instance/create")
    public String createInstance() {
        return "create instance success";
    }
}
