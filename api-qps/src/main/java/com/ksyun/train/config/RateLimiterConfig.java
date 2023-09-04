package com.ksyun.train.config;

import com.ksyun.train.util.JsonParser;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author : zhaji
 * @create 2023/7/17 22:29
 */
public class RateLimiterConfig {
    private static Map<String, Map<String, Integer>> qpsConfig = new HashMap<>();
    public static int getQpsLimit(String userId, String apiname){
        Map<String,Integer> api = qpsConfig.get(userId);
        if(api != null){
            Integer limitCount = api.get(apiname);
            return limitCount != null ? limitCount: 0;
        }
        return 0;
    }
    public static void loadConfig(Properties properties){
        for (String userId : properties.stringPropertyNames()) {
            String propertyValue = properties.getProperty(userId);
            Map<String,Integer> api = JsonParser.parseJsonConfig(propertyValue);
            qpsConfig.put(userId, api);
        }
    }

}
