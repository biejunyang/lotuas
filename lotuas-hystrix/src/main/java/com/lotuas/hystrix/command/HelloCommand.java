package com.lotuas.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HelloCommand extends HystrixCommand<String> {
    private String url;

    private CloseableHttpClient httpClient;

    public HelloCommand(String url) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGrouup"));
        this.url=url;
        this.httpClient= HttpClients.createDefault();
    }

    @Override
    protected String run() throws Exception {
        HttpGet httpGet=new HttpGet(url);
        HttpResponse response=httpClient.execute(httpGet);
        return EntityUtils.toString(response.getEntity());
    }

    @Override
    protected String getFallback() {
        System.out.println("执行Hello Command 回退方法");
        return "error";
    }
}
