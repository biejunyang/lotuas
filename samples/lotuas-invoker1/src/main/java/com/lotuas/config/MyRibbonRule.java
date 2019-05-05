package com.lotuas.config;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.List;

public class MyRibbonRule implements IRule {

    private ILoadBalancer lb;

    public MyRibbonRule() {
    }

    public MyRibbonRule(ILoadBalancer lb) {
        this.lb = lb;
    }

    @Override
    public Server choose(Object o) {
        List<Server> serverList=lb.getAllServers();
        System.out.println("fetched eureka server list:");
        for(Server s: serverList){
            System.out.println(s.getHostPort());
        }
        return serverList.get(0);
    }

    @Override
    public void setLoadBalancer(ILoadBalancer iLoadBalancer) {
        this.lb=iLoadBalancer;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return this.lb;
    }
}
