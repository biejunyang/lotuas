package com.lotuas.feign.service;

import feign.Param;
import feign.RequestLine;

public interface FeignHelloService {

    @RequestLine("GET /syaHelo?name={name}")
    String syaHello(@Param("name") String name);

    @RequestLine("GET /syaHelo?name={name}&age={age}")
    String syaHello(@Param("age") int age, @Param("name") String name);
}
