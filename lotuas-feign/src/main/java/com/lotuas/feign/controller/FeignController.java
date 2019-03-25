package com.lotuas.feign.controller;

import com.lotuas.feign.dto.UserDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {


    @RequestMapping(name = "/syaHelo", method = RequestMethod.GET)
    public String sayHello(String name, Integer age){
        System.out.println(name);
        return "hello, name: "+name+", age: "+age;

    }


    @RequestMapping(name = "/syaHelo2", method = RequestMethod.GET)
    public String sayHello2(UserDto userDto){
        return "hello, name: "+userDto.getName()+", age: "+userDto.getAge();
    }
}
