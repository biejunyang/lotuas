package com.lotuas.hystrix.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HystrixController {

    @GetMapping("/normalHello")
    public String normalHello(){
        return "normal hello";
    }



    @GetMapping("/errorHello")
    public String errorHello() throws InterruptedException {
        Thread.sleep(10000);
        return "error hello";
    }
}
