package com.lotuas.gateway.config;

import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public PatternServiceRouteMapper patternServiceRouteMapper(){
        return new PatternServiceRouteMapper("(zuul)-(?<module>.+)-(service)", "${module}/**");
    }
}
