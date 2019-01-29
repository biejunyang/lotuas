package com.lotuas.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;

@RestController
@Configuration
public class InvokerController {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


    @Autowired
    private LoadBalancerClient lbClient;


    @Autowired
    private SpringClientFactory springClientFactory;

    @Autowired
    private DiscoveryClient discoveryClient;


    @GetMapping("ribbonConfig")
    public void ribbonConfig(){
        System.out.println("=======Ribbon默认配置：");
        System.out.println(springClientFactory.getLoadBalancer("default").getClass().getName());
        ZoneAwareLoadBalancer defaultLb=(ZoneAwareLoadBalancer)springClientFactory.getLoadBalancer("default");
        System.out.println(defaultLb.getRule().getClass());
        System.out.println(defaultLb.getPing().getClass());

        System.out.println("=======lotuas-sp1配置：");
        System.out.println(springClientFactory.getLoadBalancer("lotuas-sp1").getClass().getName());
        ZoneAwareLoadBalancer sp1Lb=(ZoneAwareLoadBalancer)springClientFactory.getLoadBalancer("lotuas-sp1");
        System.out.println(sp1Lb.getRule().getClass());
        System.out.println(sp1Lb.getPing().getClass());
    }

    @GetMapping("/hello")
    public String hello(String name){
        String result=restTemplate().getForObject("http://lotuas-sp1/hello?name="+name, String.class);
        return result;
    }

    @GetMapping("/findPerson/{personId}")
    public String findPerson(@PathVariable("personId") Integer personId){
        String result=restTemplate().getForObject("http://lotuas-sp1/findPerson/3", String.class);
        return result;
    }


    @GetMapping("/router")
    public void router(){
        List<ServiceInstance> ins=getServiceInstances();
        for(ServiceInstance service: ins){
            EurekaDiscoveryClient.EurekaServiceInstance esi=(EurekaDiscoveryClient.EurekaServiceInstance)service;
            InstanceInfo info=esi.getInstanceInfo();
            System.out.println(info.getAppName()+"----"+info.getInstanceId()+"---"+info.getStatus());
        }
    }

    private List<ServiceInstance> getServiceInstances(){
        List<String> serviceIds=discoveryClient.getServices();
        List<ServiceInstance> result=new ArrayList<>();
        for(String sid: serviceIds){
            result.addAll(discoveryClient.getInstances(sid));
        }

        return result;
    }

}
