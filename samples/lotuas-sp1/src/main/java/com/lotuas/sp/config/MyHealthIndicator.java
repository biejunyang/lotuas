package com.lotuas.sp.config;

import com.lotuas.sp.controller.HelloController;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;


@Component
public class MyHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        //实现数据库连接或者第三方服务访问测试
        if(HelloController.canVistDb){
            return new Health.Builder(Status.UP).build();
        }else{
            return new Health.Builder(Status.DOWN).build();
        }
    }
}


