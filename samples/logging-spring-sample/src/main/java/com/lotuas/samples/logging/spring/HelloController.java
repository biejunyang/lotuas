package com.lotuas.samples.logging.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("hello")
    public String hello(String name){
        Log log=LogFactory.getLog(HelloController.class);
        log.info("hello,info");
        log.warn("hello, warn");
        return "hello, "+name;
    }
}
