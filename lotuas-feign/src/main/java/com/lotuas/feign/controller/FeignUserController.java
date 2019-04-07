package com.lotuas.feign.controller;

import com.lotuas.feign.dto.UserDto;
import com.lotuas.feign.service.FeignUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/feign/user")
public class FeignUserController {

    @Autowired
    private FeignUserClient feignUserClient;

    @GetMapping()
    public List<UserDto> findUser(UserDto userDto){
        Map<String, Object> paramMap=new HashMap<>();
        if(userDto.getUserId()!=null){
            paramMap.put("userId", userDto.getUserId());
        }

        if(userDto.getName()!=null && !"".equals(userDto.getName())){
            paramMap.put("name", userDto.getName());
        }
//        return feignUserClient.findUser(paramMap);
        return feignUserClient.findUser(userDto.getUserId(), userDto.getName());
    }


    @PostMapping
    public UserDto addUesr(@RequestBody UserDto userDto){
        return feignUserClient.addUser(userDto);
    }

    @PutMapping
    public UserDto updateUesr(@RequestBody UserDto userDto){
        return feignUserClient.updateUser(userDto);
    }

    @DeleteMapping("{userId:\\d+}")
    public UserDto deleteUesr(@PathVariable("userId") int userId){
        return feignUserClient.deleteUser(userId);
    }

}
