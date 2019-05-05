package com.lotuas.hystrix.controller;

import com.lotuas.hystrix.dto.UserDto;
import com.lotuas.hystrix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<UserDto> findUser(UserDto userDto){
        return userService.findUser(userDto);
    }


    @PostMapping
    public UserDto addUesr(@RequestBody UserDto userDto){
        return userService.addUesr(userDto);
    }

    @PutMapping
    public UserDto updateUesr(@RequestBody UserDto userDto){
        userService.updateUesr(userDto);
        return userDto;
    }

    @DeleteMapping("{userId:\\d+}")
    public UserDto deleteUesr(@PathVariable("userId") int userId){
        userService.deleteUesr(userId);
        return null;
    }

}
