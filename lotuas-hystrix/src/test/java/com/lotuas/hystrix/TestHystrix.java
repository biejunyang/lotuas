package com.lotuas.hystrix;

import com.lotuas.hystrix.command.HelloCommand;
import org.junit.Test;

public class TestHystrix {

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
