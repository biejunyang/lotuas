package com.lotuas;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LoadBalancerTest {

    private List<Server> serverList;

    private ILoadBalancer loadBalancer;

    @Before
    public void init(){
        serverList=new ArrayList<>();
        serverList.add(new Server("localhost",8080));
        serverList.add(new Server("localhost",8081));
        serverList.add(new Server("localhost",8082));

        loadBalancer=new BaseLoadBalancer();
        loadBalancer.addServers(serverList);
    }

    @Test
    public void testLoadBalancer(){
        for(int i = 0; i < 10; i++){
            Server server=loadBalancer.chooseServer(null);
            System.out.println(server);
        }
        Assert.assertTrue(true);

    }


    @Test
    public void testLoadBalancerRule(){
        BaseLoadBalancer lb=(BaseLoadBalancer)loadBalancer;
        lb.setRule(new MyRule(lb));
        for(int i = 0; i < 50; i++){
            Server server=loadBalancer.chooseServer(null);
            System.out.println(server);
        }
        Assert.assertTrue(true);
    }

    @Test
    public void testLoadBalancerPing(){
        try {
            BaseLoadBalancer lb=(BaseLoadBalancer)loadBalancer;
            lb.setPing(new MyPing());
            lb.setPingInterval(2);
            Thread.sleep(6000L);

            for(Server server : lb.getAllServers()){
                System.out.println(server.getHostPort()+"状态："+ server.isAlive());
            }
            Assert.assertTrue(true);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

}
