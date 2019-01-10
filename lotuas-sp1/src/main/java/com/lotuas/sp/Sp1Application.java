package com.lotuas.sp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;;import java.util.Scanner;

@SpringBootApplication
@EnableEurekaClient
public class Sp1Application {

    public static void main(String[] args){
//        SpringApplication.run(Sp1Application.class, args);

        Scanner scanner=new Scanner(System.in);
        String port=scanner.nextLine();
        new SpringApplicationBuilder(Sp1Application.class)
                .properties("server.port="+port).run(args);

    }
}

