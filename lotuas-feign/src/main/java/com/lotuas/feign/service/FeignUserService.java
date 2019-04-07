package com.lotuas.feign.service;


import com.lotuas.feign.dto.UserDto;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

import java.util.List;
import java.util.Map;

@Headers({"Accept: application/json;charset=utf-8", "Content-Type: application/json;charset=utf-8"})
public interface FeignUserService {

    @RequestLine("GET /user")
    List<UserDto> findUser(@QueryMap Map<String, Object> queryMap);

    @RequestLine("GET /user")
    List<UserDto> findUser(@Param("userId") Integer userId);

    @RequestLine("POST /user")
    UserDto addUser(UserDto userDto);

    @RequestLine("PUT /user")
    UserDto updateUser(UserDto userDto);

    @RequestLine("DELETE /user/{userId}")
    UserDto deleteUser(@Param("userId") int userId);

}
