package com.lotuas.samples.mybatis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/name")
    public String hello(String name){
        return "hello, "+name;
    }
}
