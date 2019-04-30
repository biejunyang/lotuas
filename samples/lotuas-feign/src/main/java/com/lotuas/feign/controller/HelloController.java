package com.lotuas.feign.controller;

import com.lotuas.feign.dto.UserDto;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {


    @GetMapping("/hello1")
    public String hello1(String name, Integer age){
        System.out.println(name);
        return "hello1, name: "+name+", age: "+age;
    }



    @GetMapping("/hello2")
    public String hello2(UserDto userDto){
        return "hello2, name: "+userDto.getName()+", age: "+userDto.getAge();
    }
}
