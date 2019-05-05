package com.lotuas.hystrix.command;

import com.netflix.hystrix.*;

public class RunCommand extends HystrixCommand<String> {
    private String msg;

    public RunCommand(String msg) {

//        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroupKey"))
//                .andCommandKey(HystrixCommandKey.Factory.asKey("runCommand"))
//                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
//                        .withExecutionTimeoutInMilliseconds(500)));

        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroupKey"));
        this.msg = msg;

    }

    @Override
    public String run() {
        System.out.println(msg);
        return "success";
    }
}
