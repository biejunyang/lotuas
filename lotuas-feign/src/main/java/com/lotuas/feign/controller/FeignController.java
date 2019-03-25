package com.lotuas.feign.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

    @RequestMapping("/syaHelo")
    public String sayHello(String name){
        System.out.println(name);
        return "hello, "+name;
    }
}
