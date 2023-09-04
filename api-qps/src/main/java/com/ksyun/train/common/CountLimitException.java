package com.ksyun.train.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author : zhaji
 * @create 2023/7/17 22:04
 */
@ResponseStatus(HttpStatus.CONFLICT) // 返回状态码409
public class CountLimitException extends RuntimeException {
    public CountLimitException(){
        super("request too much");
    }
}
