package com.lotuas.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
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
    private DiscoveryClient discoveryClient;


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
