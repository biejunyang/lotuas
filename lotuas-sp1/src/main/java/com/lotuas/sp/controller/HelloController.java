package com.lotuas.sp.controller;

import com.lotuas.sp.dto.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {

    public static boolean canVistDb=false;


    @GetMapping("/hello")
    public String hello(String name){
        System.out.println("hello, "+name);
        return "hello, " + name;
    }


    @GetMapping("/db/{canVistDb}")
    public String setConnectState(@PathVariable("canVistDb") Boolean canVistDb){
        HelloController.canVistDb=canVistDb;
        return "当前数据库连接是否正常："+canVistDb;
    }


    @GetMapping("/findPerson/{personId}")
    public Person findPerson(@PathVariable("personId") Integer personId, HttpServletRequest request){
        Person person=new Person();
        person.setId(personId);
        person.setName("张三");
        person.setAge(30);
        person.setMessage(request.getRequestURL().toString());
        return person;
    }
}
