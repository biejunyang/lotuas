package com.lotuas.oauth2;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@EnableWebSecurity
@SpringBootApplication
public class OAuth2ServerApplication {

    public static void main(String[] args){
        SpringApplication.run(OAuth2ServerApplication.class, args);

    }


}
