package com.lotuas.samples.oauth2.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@SpringBootApplication
@RestController
public class OAuth2ServerApplication {

    public static void main(String[] args){
        SpringApplication.run(OAuth2ServerApplication.class, args);

    }

    @RequestMapping("/user")
    @ResponseBody
    public Principal user(Principal user) {
        return user;
    }


    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }
}
