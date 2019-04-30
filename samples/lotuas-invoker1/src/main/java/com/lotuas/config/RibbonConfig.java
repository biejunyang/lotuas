package com.lotuas.config;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;

@RibbonClient(name = "lotuas-sp1", configuration = RibbonConfig.class)
public class RibbonConfig {

    @Bean
    public MyRibbonRule ribbonRule(){
        return new MyRibbonRule();
    }

}
