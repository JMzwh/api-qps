package com.ksyun.train.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * @Author : zhaji
 * @create 2023/7/17 22:48
 */
public class JsonParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Integer> parseJsonConfig(String jsonConfig) {
        try {
            return objectMapper.readValue(jsonConfig, new TypeReference<Map<String, Integer>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
