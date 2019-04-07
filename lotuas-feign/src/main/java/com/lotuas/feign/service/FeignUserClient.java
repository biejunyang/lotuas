package com.lotuas.feign.service;

import com.lotuas.feign.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("lotuas-sp1")
public interface FeignUserClient {

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    List<UserDto> findUser(@RequestParam Integer userId, @RequestParam String name);

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    List<UserDto> findUser(@RequestParam Map<String, Object> params);


    @RequestMapping(value = "/user", method = RequestMethod.POST)
    UserDto addUser(UserDto userDto);

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    UserDto updateUser(UserDto userDto);

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
    UserDto deleteUser(@PathVariable("userId") int userId);

}
