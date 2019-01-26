package com.lotuas;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;

public class MyPing implements IPing {
    @Override
    public boolean isAlive(Server server) {
        System.out.println("这是自定义IPing实现类："+server.getHostPort());
        return true;
    }
}
