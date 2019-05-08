package com.lotuas.samples.mybatis;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController implements ApplicationContextAware {

    private ApplicationContext appContext;


    @GetMapping("/hello")
    public String hello(String name){
        System.out.println(appContext.getBean(HelloService.class));
        System.out.println(appContext.getBean(HelloController.class));
        return "hello, "+name;
    }



    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(applicationContext);
        System.out.println(applicationContext.getBean(HelloController.class));
        this.appContext=applicationContext;
    }
}
