package com.lotuas.starter.spring.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;


@ComponentScan(excludeFilters = {@ComponentScan.Filter(type= FilterType.ANNOTATION, classes = {Controller.class})})
@Configuration
public class AppConfig {

}
