package com.lotuas.samples.mybatis;

import com.lotuas.samples.mybatis.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController implements ApplicationContextAware {

    private ApplicationContext appContext;


    @Autowired
    private HelloService helloService;

    @GetMapping("/hello")
    public String hello1(String name){
        for(User usre: helloService.listUser()){
            System.out.println(usre);
        }
        return "hello, "+name;
    }

    @PostMapping("/hello")
    public String hello2(String name){
        return "hello, "+name;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(applicationContext);
        System.out.println(applicationContext.getBean(HelloController.class));
        this.appContext=applicationContext;
    }
}
