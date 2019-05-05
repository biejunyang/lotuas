package com.lotuas.feign.config;

import feign.Client;
import feign.Request;
import feign.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

public class MyFeignClient implements Client {

    @Override
    public Response execute(Request request, Request.Options options) throws IOException {
        System.out.println("##############自定义Feign客户端##########");
        try {
            HttpRequestBase httpRequest=new HttpRequestBase() {
                @Override
                public String getMethod() {
                    return request.method();
                }
            };
            httpRequest.setURI(new URI(request.url()));
            CloseableHttpClient httpClient= HttpClients.createDefault();

            CloseableHttpResponse response=httpClient.execute(httpRequest);

            Response feignResponse=Response.create(response.getStatusLine().getStatusCode(), null,
                    new HashMap<>(),
                    EntityUtils.toByteArray(response.getEntity()));

            return feignResponse;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new IOException();
        }
    }
}
