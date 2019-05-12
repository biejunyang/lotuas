package com.lotuas.samples.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackageClasses = MainClass.class)
@ComponentScan(basePackages = "com.lotuas")
public class MainClass {
    public static void main(String[] args){
        SpringApplication.run(MainClass.class, args);
    }
}
