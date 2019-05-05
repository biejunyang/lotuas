package com.lotuas.feign.controller;

import com.lotuas.feign.dto.UserDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping()
    public List<UserDto> findUser(UserDto userDto){
        System.out.println("query params：userId = "+userDto.getUserId()+"&name="+userDto.getName()+"&sex="+userDto.getSex());
        List<UserDto> users=new ArrayList<>();
        for(int i=0; i<10; i++){
            UserDto user=new UserDto();
            user.setUserId(i+1);
            user.setName("张三"+i);
            user.setAge(20+i);
            user.setSex(0);
            user.setBirthday(new Date());
            users.add(user);
        }
        return users;
    }


    @PostMapping
    public UserDto addUesr(@RequestBody UserDto userDto){
        System.out.println("add user: userId = "+userDto.getUserId()+"&name="+userDto.getName()+"&sex="+userDto.getSex()+"&birthday="+userDto.getBirthday());
        return userDto;
    }

    @PutMapping
    public UserDto updateUesr(@RequestBody UserDto userDto){
        System.out.println("update user: userId = "+userDto.getUserId()+"&name="+userDto.getName()+"&sex="+userDto.getSex()+"&birthday="+userDto.getBirthday());
        return userDto;
    }

    @DeleteMapping("{userId:\\d+}")
    public UserDto deleteUesr(@PathVariable("userId") int userId){
        System.out.println("delete user: userId = "+userId);
        UserDto userDto=new UserDto();
        userDto.setUserId(userId);
        return userDto;
    }

}
