package com.lotuas.starter.spring.web.controller;

import com.lotuas.starter.spring.web.service.TestService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController implements ApplicationContextAware {

    private ApplicationContext appConext;

    @GetMapping("/test1")
    public String test1(){
        System.out.println(appConext.getBean(TestService.class));
        System.out.println(appConext.getBean(TestController.class));
        return "test1";
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appConext=applicationContext;
    }
}
