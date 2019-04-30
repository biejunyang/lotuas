package com.lotuas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
//@EnableDiscoveryClient
@EnableEurekaClient
public class EkClient1Appliction
{
    public static void main( String[] args )
    {
        SpringApplication.run(EkClient1Appliction.class, args);
    }
}
