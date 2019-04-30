package com.lotuas.hystrix;

import com.lotuas.hystrix.command.HelloCommand;
import com.netflix.config.ConfigurationManager;
import org.junit.Before;
import org.junit.Test;

public class TestHystrix {

    @Before
    private void init(){


        ConfigurationManager.getConfigInstance()
                .setProperty("hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds", 1000);


    }

    @Test
    public void testNormal(){
        String url="http://localhost:8081/normalHello";
        HelloCommand helloCommand=new HelloCommand(url);

        String result=helloCommand.execute();

        System.out.println("正常请求:"+result);
    }


    @Test
    public void testError(){
        String url="http://localhost:8081/errorHello";
        HelloCommand helloCommand=new HelloCommand(url);

        String result=helloCommand.execute();

        System.out.println("异常请求:"+result);
    }
}
