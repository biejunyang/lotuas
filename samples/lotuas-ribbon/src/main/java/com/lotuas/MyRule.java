package com.lotuas;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.Random;

public class MyRule implements IRule {

    private ILoadBalancer lb;

    public MyRule(ILoadBalancer lb) {
        this.lb = lb;
    }

    @Override
    public Server choose(Object o) {
        List<Server> servers=lb.getAllServers();
        Random random=new Random();
        int index=random.nextInt(servers.size());
        System.out.println("index:"+index);
        return servers.get(index);



    }

    @Override
    public void setLoadBalancer(ILoadBalancer iLoadBalancer) {
        this.lb=iLoadBalancer;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return lb;
    }
}
