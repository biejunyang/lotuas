package com.lotuas.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class RunCommand extends HystrixCommand<String> {
    private String msg;

    public RunCommand(String msg) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroupKey"));
        this.msg = msg;
    }

    @Override
    public String run() {
        System.out.println(msg);
        return "success";
    }
}
