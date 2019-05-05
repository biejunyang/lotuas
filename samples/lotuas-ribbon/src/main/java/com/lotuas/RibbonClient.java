package com.lotuas;


import com.netflix.client.ClientFactory;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.niws.client.http.RestClient;

public class RibbonClient {

    public static void main(String[] args) throws Exception {
        //1、设置服务器列表
        ConfigurationManager.getConfigInstance().setProperty("my-client.ribbon.listOfServers",
                "localhost:8788,localhost:8789");


        //2、湖区Rest请求客户端
        RestClient client=(RestClient) ClientFactory.getNamedClient("my-client");

        //3、创建请求实例
        HttpRequest request=HttpRequest.newBuilder().uri("/findPerson/1").build();

        //4、负载均衡,调用请求
        for(int i=0; i<10; i++){
            HttpResponse response=client.executeWithLoadBalancer(request);
            String result = response.getEntity(String.class);
            System.out.println(result);
        }

        System.out.println("调用结束!!!");

    }
}
