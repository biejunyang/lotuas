package com.lotuas.feign.service;

import feign.RequestLine;

public interface FeignHelloService {

    @RequestLine("GET /syaHelo")
    public String syaHello(String name);
}
