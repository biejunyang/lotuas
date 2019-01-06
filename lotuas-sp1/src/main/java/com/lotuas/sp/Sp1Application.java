package com.lotuas.sp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;;

@SpringBootApplication
@EnableEurekaClient
public class Sp1Application {

    public static void main(String[] args){
        SpringApplication.run(Sp1Application.class, args);
    }
}

