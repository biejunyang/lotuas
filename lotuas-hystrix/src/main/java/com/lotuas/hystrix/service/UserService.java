package com.lotuas.hystrix.service;

import com.lotuas.hystrix.dto.UserDto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "findUserFallback", groupKey = "exampleKey",
            commandKey = "findyUserKey", threadPoolKey = "exampleThreadll",
            commandProperties = {}, threadPoolProperties = {})
    public List<UserDto> findUser(UserDto userDto){
        StringBuilder urlBuf=new StringBuilder();
        List<Object> params=new ArrayList<>();
        if(userDto.getUserId()!=null){
            urlBuf.append("userId={userId}&");
            params.add(userDto.getUserId());
        }

        if(userDto.getName()!=null && !"".equals(userDto.getName())){
            urlBuf.append("name={name}&");
            params.add(userDto.getName());
        }
        if(params.size()>0){
            urlBuf.deleteCharAt(urlBuf.lastIndexOf("&"));
        }
        List<UserDto> users=restTemplate.getForObject("http://lotuas-sp1/user?"+urlBuf, List.class, params.toArray());
        return users;
    }


    public UserDto addUesr(UserDto userDto){
        return restTemplate.postForObject("http://lotuas-sp1/user", userDto, UserDto.class);
    }


    public void updateUesr(UserDto userDto){
        restTemplate.put("http://lotuas-sp1/user", userDto);
    }

    public void deleteUesr(int userId){
        restTemplate.delete("http://lotuas-sp1/user/{userId}", userId);
    }



    public List<UserDto> findUserFallback(UserDto userDto){
        List<UserDto> users=new ArrayList<>();
        UserDto user=new UserDto();
        user.setUserId(userDto.getUserId());
        user.setName(userDto.getName());
        users.add(user);
        return users;
    }
}
