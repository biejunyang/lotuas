package com.lotuas.starter.spring.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
//@EnableWebMvc
@ComponentScan(basePackages="*", useDefaultFilters=false,
        includeFilters=@ComponentScan.Filter(Controller.class))
public class WebConfig extends WebMvcConfigurationSupport {
}
