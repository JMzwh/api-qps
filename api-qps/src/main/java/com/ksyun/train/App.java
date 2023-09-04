package com.ksyun.train;

import com.ksyun.train.config.RateLimiterConfig;
import com.ksyun.train.config.RateLimiterProperties;
import com.ksyun.train.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * @Author : zhaji
 * @create 2023/7/17 20:32
 */
@SpringBootApplication

public class App {
    @Autowired
    private RateLimiterProperties rateLimiterProperties;
    public static void main(String[] args) throws IOException {
        App a= new App();
        InputStream in = a.getClass().getResourceAsStream("/ratelimiter.properties");
        Properties properties = new Properties();
        properties.load(in);
        RateLimiterConfig.loadConfig(properties);
        SpringApplication.run(App.class);

    }




}