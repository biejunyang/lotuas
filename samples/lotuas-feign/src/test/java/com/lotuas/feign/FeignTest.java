package com.lotuas.feign;

import com.lotuas.feign.config.MyFeignContract;
import com.lotuas.feign.config.MyFeignInterceptor;
import com.lotuas.feign.dto.UserDto;
import com.lotuas.feign.service.FeignHelloService;
import com.lotuas.feign.service.FeignUserService;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeignTest {

    @Test
    public void feignHelloTest(){

        FeignHelloService helloService= Feign.builder().target(FeignHelloService.class, "http://localhost:8080/");

        System.out.println(helloService.hello1("张三"));

        System.out.println(helloService.hello1(20, "万古"));

        System.out.println(helloService.hello2(20, "为错的地方"));
    }


    @Test
    public void feignUesrService(){
        FeignUserService userService= Feign.builder().encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()).target(FeignUserService.class, "http://localhost:8080/");

        Map<String, Object> queryMap=new HashMap<>();
        queryMap.put("userId", 1);
        queryMap.put("name", "张三");
        List<UserDto> users=userService.findUser(queryMap);
        for(UserDto userDto: users){
            System.out.println(userDto);
        }

        UserDto addUser=new UserDto();
        addUser.setUserId(250);
        addUser.setName("二百五");
        addUser.setSex(1);
        addUser.setAge(50);
        addUser.setBirthday(new Date());
        System.out.println(userService.addUser(addUser));

        UserDto updateUser=new UserDto();
        updateUser.setUserId(250);
        updateUser.setName("百五");
        updateUser.setSex(0);
        updateUser.setAge(20);
        updateUser.setBirthday(new Date());
        System.out.println(userService.updateUser(updateUser));

        System.out.println(userService.deleteUser(10));
    }
}
