package com.lotuas.sys;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication

//启用Spring Security安全认证机制
@EnableWebSecurity
public class SysApplication {

    public static void main(String[] args){
        SpringApplication.run(SysApplication.class, args);

    }
}

