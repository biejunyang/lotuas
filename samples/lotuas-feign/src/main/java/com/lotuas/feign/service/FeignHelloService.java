package com.lotuas.feign.service;

import feign.Param;
import feign.RequestLine;

public interface FeignHelloService {

    @RequestLine("GET /hello1?name={name}")
    String hello1(@Param("name") String name);

    @RequestLine("GET /hello1?name={name}&age={age}")
    String hello1(@Param("age") int age, @Param("name") String name);

    @RequestLine("GET /hello2?name={name}&age={age}")
    String hello2(@Param("age") int age, @Param("name") String name);

}
