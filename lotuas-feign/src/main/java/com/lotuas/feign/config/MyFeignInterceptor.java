package com.lotuas.feign.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class MyFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Content-Type","application/json;charset=utf-8");
    }
}
