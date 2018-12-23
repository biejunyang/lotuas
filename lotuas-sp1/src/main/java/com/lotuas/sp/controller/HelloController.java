package com.lotuas.sp.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    public String hello(String name){
        System.out.println("hello, "+name);
        return "hello, " + name;
    }
}
