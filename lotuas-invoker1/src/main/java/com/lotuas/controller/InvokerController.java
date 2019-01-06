package com.lotuas.controller;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@Configuration
public class InvokerController {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


    @GetMapping("/hello")
    public String hello(String name){
        Map<String, Object> paramMap=new HashMap<>();
        paramMap.put("name", "zhangsan");
        String result=restTemplate().getForObject("http://lotuas-sp1/hello", String.class, paramMap);
        return result;
    }


}
