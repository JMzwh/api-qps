package com.ksyun.train.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author : zhaji
 * @create 2023/7/17 23:18
 */
@Component
@ConfigurationProperties(prefix = "ratelimiter")
@PropertySource("classpath:ratelimiter.properties")
public class RateLimiterProperties {
    // 定义与配置文件中的属性对应的字段
    private String property1;
    private String property2;
}
