package com.lotuas.feign.config;

import com.lotuas.feign.annotation.MyUrl;
import feign.*;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }


    @Bean
    public Contract feignContract(){
        return new SpringMvcContract(){
            @Override
            protected void processAnnotationOnMethod(MethodMetadata data, Annotation annotation, Method method) {
                //调用Spring Cloud父类翻译器，仍然支持Spring Cloud的注解
                super.processAnnotationOnMethod(data, annotation, method);

                //处理自定义的注解
                if(annotation.getClass()== MyUrl.class){
                    MyUrl myUrlAnn=(MyUrl)annotation;
                    data.template().append(myUrlAnn.url());
                    data.template().method(myUrlAnn.method());
                }

            }
        };
    }


    @Bean
    public RequestInterceptor feignInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                System.out.println("feign请求拦截器");
            }
        };
    }
}
